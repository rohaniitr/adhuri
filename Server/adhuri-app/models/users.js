var bcrypt = require('bcrypt-nodejs');
var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost/rohan_db');

var UserSchema = new mongoose.Schema({
  email: {
    type: 'String',
    unique: true,
    required: true
  },
  name: {
    type: String,
    required: true,
    trim: true
  },
  password: {
    type: String,
    required: true,
  },
  bio: {
    type: String,
    trim: true
  },
  gender: {
    type: String,
    trim: true
  },
  image: {
    type: String,
    trim: true
  },
  tags: [{
    type: String
  }]
});

UserSchema.pre('save', function (next) {
  var user = this;

  // var salt = bcrypt.genSaltSync(10);
  //Get salt Async
  bcrypt.genSalt(10, function(err, salt){
    if (err)
      return next(err);

    bcrypt.hash(user.password, salt, null, function (err, hash){
      if (err)
        return next(err);

      user.password = hash;
      next();
    });
  });
});

var User = mongoose.model('User', UserSchema);
module.exports = User;
