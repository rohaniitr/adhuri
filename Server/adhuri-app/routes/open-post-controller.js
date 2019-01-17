var express = require('express');
var router = express.Router();
var bodyParser = require('body-parser');
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
   console.log('AddOpenPost');

   if(!req.body.content || !req.body.time || !req.body.tags || !req.body.tags[0])
      return res.status(401).send({ auth: false, message: 'Sorry, you provided wrong info' });

    var openPost = new Post({
       userId: req.userId,
       content: req.body.content,
       time: req.body.time,
       tags: req.body.tags[0],
       isOpen: true
    });

    openPost.save().then( function(postData){
      var hashDataType = getHashDataType(postData.tags[0]);
      if(hashDataType == null)
        throw "Invalid Tag";
      return hashDataType.create({postId:postData._id, isOpen:true});
    }).then(function(hashData){
      console.log("hashData : " + hashData);
      openPost.hashId = [hashData._id];
      return openPost.save();
    }).then(function(postData){
      console.log("postData : " + postData);
      res.status(200).send(postData);
    }).catch(function(error){
      console.log("BAP RE!! ERROR....\n" + error);
      return res.status(404).send(error);
    });
});

router.post('/update', function(req, res){
   console.log('UpdateOpenPost');

   if(!req.body.postId || !req.body.content || !req.body.tags || !req.body.tags[0])
      return res.status(401).send({ auth: false, message: 'Sorry, you provided wrong info' });

    Post.findOne({_id:req.body.postId}).exec().then(function(postData){
      if(!postData)
        throw "Post not found";
      //Can be handled from  Android client
      if(postData.userId != req.userId)
        throw "User does not have permission to delete the post";

      if(postData.tags[0] != req.body.tags[0]){
        //Remove from Old HashTable. Add to new HashTable.
        var OldHashDataType = getHashDataType(postData.tags[0]);
        OldHashDataType.findOneAndDelete({_id:postData.hashId[0]}).exec();
        var NewHashDataType = getHashDataType(req.body.tags[0]);
        return NewHashDataType.create({postId:postData._id, isOpen:true}).then(function(hashData){
          console.log("newhashData : " + hashData);
          postData.hashId = [hashData._id];
          postData.tags = req.body.tags;
          return postData;
        });
      }
      else{
        return postData;
      }
    }).then(function(postData){
      //Update OpenPost data with new values.
      postData.content = req.body.content;

      postData.save().then(function(updatedPostData){
        console.log("UpdatedPostData : " + updatedPostData);
        return res.status(200).json(updatedPostData);
      });
    }).catch(function(error){
      console.error(error);
      return res.status(404).json({error:error});
    });
});

router.get('/getPost/:postId', function(req,res){
  console.log('GetPost');
  if(!req.params.postId)
    return res.status(401).json({ auth: false, message: 'Sorry, you provided wrong info' });

   //Send suggestions

});

router.get('/remove/:postId', function(req,res){
  console.log('RemoveOpenPost');
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
      return Suggestion.remove({postId: postData._id}).exec().then(function(data){
        console.log(data);
        return res.status(200).json({message:"Post removed successfully"});
      })
    });
  }).catch(function(error){
    console.log(error);
    return res.status(404).json({error: error});
  });
});

router.post('/close', function(req, res){
   console.log('CloseOpenPost\n' + req.body.acceptedSuggestionId.length);

   if(!req.body.postId || !req.body.content || !req.body.acceptedSuggestionId || !req.body.tags)
      return res.status(401).send({ auth: false, message: 'Sorry, you provided wrong info' });

    for(var i=0; i<req.body.acceptedSuggestionId.length;i++){
      console.log(req.body.acceptedSuggestionId[i]);
      Suggestion.update(
        { _id: req.body.acceptedSuggestionId[i] },
        { isAccepted: true}
      ).exec().catch(function(err){
        console.log("error Sugg : " + err);
      });
    }

    Post.findOne({_id:req.body.postId}).exec().then(function(postData){
      if(!postData)
        throw "Post not found";
      //Can be handled from  Android client
      if(postData.userId != req.userId)
        throw "User does not have permission to delete the post";

      if(postData.tags[0] != req.body.tags[0]){
        //Remove from Old HashTable. Add to new HashTable.
        var OldHashDataType = getHashDataType(postData.tags[0]);
        OldHashDataType.findOneAndDelete({_id:postData.hashId[0]}).exec();
        var NewHashDataType = getHashDataType(req.body.tags[0]);
        return NewHashDataType.create({postId:postData._id, isOpen:true}).then(function(hashData){
          console.log("newhashData : " + hashData);
          postData.hashId = [hashData._id];
          postData.tags = req.body.tags;
          return postData;
        });
      }
      else{
        return postData;
      }
    }).then(function(postData){
      //Update OpenPost data with new values.
      postData.content = req.body.content;
      postData.isOpen = false;

      postData.save().then(function(updatedPostData){
        console.log("UpdatedPostData : " + updatedPostData);
        return res.status(200).json(updatedPostData);
      });
    }).catch(function(error){
      console.error(error);
      return res.status(404).json({error:error});
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
      //Rohan
      //Need to sort the posts by time.
      //Need to place upper limit for list of documents.
      promiseList.push(HashMapType.find().select("postId").exec());
    }
    return Promise.all(promiseList).catch(function(error){console.log(error);});
  }).then(function(postData){
    //Spread operator for 2D->1D in ES6
    var postIds = [].concat(...postData);
    postIds = postIds.map(function(post){return post.postId});

    return Post.find({_id:postIds, isOpen:true}).select('_id tags content time suggestionId userId')
    .lean().exec().catch(function(err){console.log();})
  }).then(function(data){
    if(!data)
      throw "Post not found";

    postList=data;
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
    //console.log("postList : " + postList);
    res.status(200).json(postList);
  }).catch(function(error){
    console.log(error);
    return res.status(404).json({error: error});
  });
});



router.get('/getUserPosts/:userId', function(req,res){
  console.log('GetOpenPost for User : ' + req.params.userId);
  var postList;

  Post.find({userId:req.params.userId, isOpen:true}).select('_id tags content time suggestionId userId')
  .lean().exec().then(function(data){
    if(!data)
      throw "Post not found";

    postList=data;

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
      promiseList.push(Users.find({_id:userIds}).select("name -_id").exec());
    }

    return Promise.all(promiseList).then(function(userList){
      return userList;
    }).catch(function(err){console.log(err);});
  }).then(function(userMatrix){
    //Add suggestion count & one suggestor name
    for(var i=0; i<postList.length; i++){
      postList[i].suggestorCount = userMatrix[i].length;
      if(userMatrix[i].length > 0)
        postList[i].suggestorName = userMatrix[i][0].name;
      delete postList[i].suggestionId;
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
