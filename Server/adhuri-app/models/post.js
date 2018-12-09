var bcrypt = require('bcrypt-nodejs');
var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost/rohan_db');

var PostSchema = new mongoose.Schema({
  userId: {
    type: String,
    required: true
  },
  content: {
    type: String,
    required: true,
    trim : true
  },
  time: {
    type: String,
    required: true
  },
  isOpen: {
    type: Boolean,
    required: true
  },
  tags: [{
    type: String,
    required: true
  }],
  suggestionId: [{
    type: String
  }],
  hashId: [{
    type: String
  }]
});

var Post = mongoose.model('Post', PostSchema);
module.exports = Post;
