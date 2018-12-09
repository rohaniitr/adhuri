var express = require('express');
var router = express.Router();
var bodyParser = require('body-parser');
var jwt = require('jsonwebtoken');
var bcrypt = require('bcrypt-nodejs');
var config = require('../config/config');
var Post = require('../models/post');
var Suggestion = require('../models/suggestion');

router.use(bodyParser.urlencoded({ extended: false }));
router.use(bodyParser.json());

router.post('/add', function(req, res){
   console.log('AddSuggestion');

   if(!req.body.postId || !req.body.content || !req.body.time)
      return res.status(401).send({ auth: false, message: 'Sorry, you provided wrong info' });

    Suggestion.create({
      userId: req.userId,
      postId: req.body.postId,
      content: req.body.content,
      time: req.body.time,
      isAccepted:false
    }).then(function(suggestionData){
      console.log("suggestionData : " + suggestionData);

      return Post.findOne({_id:req.body.postId}).exec().then(function(postData){
        if(!postData)
          throw "Post not found";

        postData.suggestionId.push(suggestionData._id);
        console.log("postdata : " + postData);
        return postData;
      }).then(function(postData){
        postData.save().then(function(postData){
          return res.status(200).json(postData);
        });
      });
    }).catch(function(err){
      console.log(err);
      return res.status(500).json({error:err});
    });
});

router.get('/get/:postId', function(req,res){
  console.log("GetSuggestions : " + req.params.postId);

  if(!req.params.postId)
    return res.status(401).send({ auth: false, message: 'Sorry, you provided wrong info' });

  Suggestion.find({postId: req.params.postId})
  .select('_id content time isAccepted').exec().then(function(suggestions){
    console.log(suggestions);
    return res.status(200).json(suggestions);
  }).catch(function(err){
    return res.status(500).json(err);
  });
});

module.exports = router;
