var express = require('express');
var router = express.Router();
var bodyParser = require('body-parser');
var jwt = require('jsonwebtoken');
var bcrypt = require('bcrypt-nodejs');
var config = require('../config/config');
var Users = require('../models/users');

router.use(bodyParser.urlencoded({ extended: false }));
router.use(bodyParser.json());

router.post('/login', function(req,res){
  console.log('\n\nLogin Req : ',req.body.email);

  if(!req.body.email || !req.body.password){
     return res.status(401).send({ auth: false, message: 'Sorry, you provided wrong info' });
  }
  
  //Login & Create new session
  Users.findOne({email : req.body.email}, function(err,userData){
    if(err)
      return res.status(500).send("There was a problem finding the user.");
    else {
      bcrypt.compare(req.body.password, userData.password, function(err,result){
        if(err)
          return res.status(404).send("Wrong credentials.");

        if(result == false)
          return res.status(500).send({ auth: false, message: 'Wrong credentials.' });
        else{
          //Login successful. Create a token.
          var token = jwt.sign({ id: userData._id }, config.secret, {
            expiresIn: 2678400 // expiry time in seconds
          });
          res.status(200).send({ auth: true, token: token });
        }
      });
    }
  });
});

router.post('/register', function(req, res){
   console.log('\n\nRegister : ' + req.body.email);

   if(!req.body.email || !req.body.password || !req.body.name){
      return res.status(401).send({ auth: false, message: 'Sorry, you provided wrong info' });
   }

    var newUser = new Users({
       name: req.body.name,
       email: req.body.email,
       password: req.body.password
    });

    newUser.save(function(err, Person){
       if(err)
          return res.status(500).send('Database error\n' + err);

        res.status(200).send({message : 'New person added'});
    });
});

module.exports = router;
