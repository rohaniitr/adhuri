var express = require('express');
var router = express.Router();
var bodyParser = require('body-parser');
var jwt = require('jsonwebtoken');
var bcrypt = require('bcrypt-nodejs');
var config = require('../config/config');
var Users = require('../models/users');
var Post = require('../models/post');
var Suggestion = require('../models/suggestion');
var HashtableModels = require('../models/hashtable-models');
var Philosophy = HashtableModels.Philosophy;
var Shayari = HashtableModels.Shayari;
var Poem = HashtableModels.Poem;
var Quote = HashtableModels.Quote;

router.use(bodyParser.urlencoded({ extended: false }));
router.use(bodyParser.json());

router.post('/add', function(req, res){
   console.log('AddClosePost');

   if(!req.body.content || !req.body.time || !req.body.tags || !req.body.tags[0])
      return res.status(401).send({ auth: false, message: 'Sorry, you provided wrong info' });

    var closePost = new Post({
       userId: req.userId,
       content: req.body.content,
       time: req.body.time,
       tags: req.body.tags[0],
       isOpen: false
    });

    Post.save().then( function(postData){
      var hashDataType = getHashDataType(postData.tags[0]);
      if(hashDataType == null)
        throw "Invalid Tag";
      return hashDataType.create({postId:postData._id, isOpen:false});
    }).then(function(hashData){
      console.log("hashData : " + hashData);
      post.hashId = [hashData._id];
      return post.save();
    }).then(function(postData){
      console.log("postData : " + postData);
      res.status(200).send(postData);
    }).catch(function(error){
      console.log("BAP RE!! ERROR....\n" + error);
      return res.status(404).send(error);
    });
});

router.get('/remove/:postId', function(req,res){
  console.log('RemoveClosePost');
  if(!req.params.postId)
     return res.status(401).json({ auth: false, message: 'Sorry, you provided wrong info' });

  Post.findOne({_id:req.params.postId}).then(function(postData){
    if(!postData)
      throw "Post not found";
    //Can be handled from  Android client
    if(postData.userId != req.userId)
      throw "User does not have permission to delete the post";

    var HashDataType = getHashDataType(postData.tags[0]);
    HashDataType.findOneAndRemove({_id:postData.hashId[0]}).exec();
    return Post.findOneAndRemove({_id:postData._id}).exec().then(function(data){
      //Need to remove suggestions too...
      return res.status(200).json({message:"Post removed successfully"});
    });
  }).catch(function(error){
    console.log(error);
    return res.status(404).json({error: error});
  });
});

router.get('/getFeed', function(req,res){
  console.log('Show feed to : ' + req.userId);
  var postList;
  console.log(req.userId);
  Users.findOne({_id:req.userId}).select("tags").exec().then(function(userData){
    var tags = userData.tags;
    promiseList = new Array();
    for(var i=0; i<tags.length; i++){
      var HashMapType = getHashDataType(tags[i]);
      if(HashMapType == null){
        console.log("Missing TAG : " + tags[i]);
        continue;
      }
      promiseList.push(HashMapType.find().select("postId").exec());
    }
    return Promise.all(promiseList).catch(function(error){console.log(error);});
  }).then(function(postData){
    //Spread operator for 2D->1D in ES6
    var postIds = [].concat(...postData);
    postIds = postIds.map(function(post){return post.postId});

    return Post.find({_id:postIds, isOpen:false}).select('_id tags content time suggestionId userId')
    .lean().exec().catch(function(err){console.log();})
  }).then(function(data){
    if(!data)
      throw "Post not found";

    postList=data;
    console.log("ROHAN : " + postList);
    // Add User data here
    promiseList = [];
    postList.forEach(function(post){
      promiseList.push(Users.findOne({_id:post.userId}).select("name picture").exec().then(function(userData){
        post.name = userData.name;
        post.picture = userData.picture;
        delete post.userId;
      }));
    });
    return Promise.all(promiseList);
  }).then(function(){
    var suggestionMatrix = postList.map(function(postData){return postData.suggestionId});

    var promiseList = [];
    for(var i=0; i<suggestionMatrix.length; i++)
      promiseList.push(Suggestion.find({_id:suggestionMatrix[i]}).select("userId -_id").exec());

    return  Promise.all(promiseList);
  }).then(function(userMatrix){
    promiseList = [];
    for(var i=0; i<userMatrix.length; i++){
      var userIds = userMatrix[i].map(function(ids){return ids.userId});
      promiseList.push(Users.find({_id:userIds}).select("name image -_id").exec());
    }

    return Promise.all(promiseList).then(function(userList){
      // console.log(userList);
      return userList;
    }).catch(function(err){console.log(err);});
  }).then(function(userMatrix){
    //Add suggestion count & one suggestor name
    for(var i=0; i<postList.length; i++){
      postList[i].suggestorCount = userMatrix[i].length;

      if(userMatrix[i] == null)
        continue;
      if(userMatrix[i].length > 0)
        postList[i].suggestorName = userMatrix[i][0].name;

      for(var j=0; j<userMatrix[i].length; j++){
        if(userMatrix[i][j].image != null){
          postList[i].suggestorName = userMatrix[i][j].name;
          postList[i].suggestorImage = userMatrix[i][j].image;
          break;
        }
      }
      delete postList[i].suggestionId;
      console.log(postList[i]);
    }
    // console.log("postList : " + postList);
    res.status(200).json(postList);
  }).catch(function(error){
    console.log(error);
    return res.status(404).json({error: error});
  });
});

router.get('/getUserPosts/:userId', function(req,res){
  console.log('GetClosePost for User : ' + req.params.userId);
  var postList;

  Post.find({userId:req.params.userId, isOpen:false}).select('_id tags content time suggestionId userId')
  .lean().exec().then(function(data){
    if(!data)
      throw "Post not found";

    postList=data;
    console.log("ROHAN : " + postList);
    // Add User data here
    promiseList = [];
    postList.forEach(function(post){
      promiseList.push(Users.findOne({_id:post.userId}).select("name image").exec().then(function(userData){
        post.name = userData.name;
        post.image = userData.image;
      }));
    });
    return Promise.all(promiseList);
  }).then(function(){
    var suggestionMatrix = postList.map(function(postData){return postData.suggestionId});

    var promiseList = [];
    for(var i=0; i<suggestionMatrix.length; i++)
      promiseList.push(Suggestion.find({_id:suggestionMatrix[i]}).select("userId -_id").exec());

    return  Promise.all(promiseList);
  }).then(function(userMatrix){
    promiseList = [];
    for(var i=0; i<userMatrix.length; i++){
      var userIds = userMatrix[i].map(function(ids){return ids.userId});
      promiseList.push(Users.find({_id:userIds}).select("picture -_id").exec());
    }

    return Promise.all(promiseList).then(function(userList){
      // console.log(userList);
      return userList;
    }).catch(function(err){console.log(err);});
  }).then(function(userMatrix){
    //Add suggestion count & ax 3 suggestor picture
    for(var i=0; i<postList.length; i++){
      postList[i].suggestorCount = userMatrix[i].length;
      //Get max 3 valid pics
      postList[i].suggestorImages = userMatrix[i].map(function(user){
        if(user.picture != null)
          return user.picture;
      });
      delete postList[i].suggestionId;
      //Works on mongoose models too
      // postList[i].suggestionId = undefined;
      console.log(postList[i]);
    }
    console.log("postList : " + postList);
    res.status(200).json(postList);
  }).catch(function(error){
    console.log(error);
    return res.status(404).json({error: error});
  });
});

function getHashDataType(tag){
  if(tag == 'Philosophy'){
    return Philosophy;
  }else if(tag == 'Shayari'){
    return Shayari;
  }else if(tag == 'Poem'){
    return Poem;
  }else if(tag == 'Quote'){
    return Quote;
  }
  return null;
}

module.exports = router;
