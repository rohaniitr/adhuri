var express = require('express');
var router = express.Router();
var bodyParser = require('body-parser');
var jwt = require('jsonwebtoken');
var bcrypt = require('bcrypt-nodejs');
var config = require('../config/config');
var Users = require('../models/users');

router.use(bodyParser.urlencoded({ extended: false }));
router.use(bodyParser.json());

router.use(function(req,res,next){
  console.log('\n\nVerifying Token from token-controller!');
  var token = req.headers['x-access-token'];

  if (!token)
    return res.status(401).send({ auth: false, message: 'No token provided.' });

  jwt.verify(token, config.secret, function(err, decoded) {
    if (err)
      return res.status(500).send({ auth: false, message: 'Failed to authenticate token.' });

    //Check if the User still exist!
    Users.findById(decoded.id, function (err, user) {
      if (err)
        return res.status(500).send("There was a problem finding the user.");
      if (!user)
        return res.status(404).send("No user found.");

      req.userId = decoded.id;
      next();
    });
  });
});

module.exports = router;
