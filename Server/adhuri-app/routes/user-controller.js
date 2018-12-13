var express = require('express');
var router = express.Router();
var bodyParser = require('body-parser');
var jwt = require('jsonwebtoken');
var bcrypt = require('bcrypt-nodejs');
var multer = require('multer');
var crypto = require('crypto');
var path = require('path');
var config = require('../config/config');
var Users = require('../models/users');

router.use(bodyParser.urlencoded({ extended: false }));
router.use(bodyParser.json());

router.post('/updateDetail', function(req,res){
  console.log('UpdateDetail');
  //Add logic for uploading profile picture.
  if (!req.body.name || !req.body.bio || !req.body.gender)
    return res.status(401).send({ auth: false, message: 'Sorry, you provided wrong info' });

  Users.findByIdAndUpdate(req.userId,
    {name:req.body.name, bio:req.body.bio, gender:req.body.gender},
    function (err, user) {
    if (err)
      return res.status(500).json({message:"There was a problem updating the user details."});
    if (!user)
      return res.status(404).json({message:"No user found."});

    res.status(200).json({
      success:true
    });
  });
});

router.get('/removeDetail', function(req,res){
  console.log('RemoveDetail');

  //Will never reach here
  if (!req.userId)
    return res.status(401).send({ auth: false, message: 'Wrong token provided.' });

    Users.findByIdAndRemove(req.userId, function (err, user) {
    if (err)
      return res.status(500).json({message:"There was a problem deleting the user."});
    if (!user)
      return res.status(404).json({message:"No user found."});

    res.status(200).json({message:'Removed user successfully.'});
  });
});

router.get('/getDetail/:userId', function(req,res){
  console.log('GetDetail');

  if(!req.params.userId){
    return res.status(401).json({ auth: false, message: 'Sorry, you provided wrong info' });
  }

  Users.findById(req.params.userId, function (err, user) {
    if (err)
      return res.status(500).json({message:"There was a problem finding the user."});
    if (!user)
      return res.status(404).json({message:"No user found."});

    res.status(200).json({
      email: user.email,
      name: user.name,
      bio: user.bio,
      gender: user.gender,
      picture: user.picture,
      tags: user.tags
    });
  });
});

router.post('/updateTags', function(req,res){
  console.log('Tags');

  if(!req.body.tags){
    return res.status(401).json({ auth: false, message: 'Sorry, you provided wrong info' });
  }

  Users.findByIdAndUpdate(req.userId, {tags: req.body.tags}, function (err, user) {
    if (err)
      return res.status(500).json({message:"There was a problem updating the user."});
    if (!user)
      return res.status(404).json({message:"No user found."});

    return res.status(200).json({message : 'Tags Updated.'});
  });
});

//Upload User Image
var destination = './public/images';
var filename = function(req, file, cb) {
      return crypto.pseudoRandomBytes(16, function(err, raw) {
        if (err)  return cb(err);
        return cb(null, "" + (raw.toString('hex')) + (path.extname(file.originalname)));
      })};

const allowedImagesExts = ['jpg', 'png', 'gif', 'jpeg']
const fileFilter =  (req, file, cb) =>
  cb(null, allowedImagesExts.includes(file.originalname.split('.').pop()))

var storage = multer.diskStorage({ destination, filename })
var uploadUsers = multer({ storage, fileFilter })

router.post("/updateImage", uploadUsers.single('upload'), function(req, res) {
  console.log(req.file);
  console.log(req.file.filename);

  Users.findOneAndUpdate(
    {_id : req.userId},
    {image : req.file.filename},
    {new : true}  //Provides  updated data to then
  ).exec().then(function(userData){
    console.log("Image updated successfully for : " + req.userId + "\n" + userData);
    return res.status(200).json({image: req.file.filename}).end();
  }).catch(function(err){
    console.log(err);
  });

});

module.exports = router;
