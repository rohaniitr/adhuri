var bcrypt = require('bcrypt-nodejs');
var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost/rohan_db');

var SuggestionSchema = new mongoose.Schema({
  userId: {
    type: String,
    required: true
  },
  postId: {
    type: String,
    required: true,
    trim : true
  },
  content: {
    type: String,
    required: true,
    trim : true
  },
  time: {
    type: String,
    required: true,
    trim: true
  },
  isAccepted: {
    type: Boolean,
    required: true,
    trim: true
  }
});

var Suggestion = mongoose.model('Suggestion', SuggestionSchema);
module.exports = Suggestion;
