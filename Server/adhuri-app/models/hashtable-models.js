var bcrypt = require('bcrypt-nodejs');
var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost/rohan_db');

var PhilosophySchema = new mongoose.Schema({
  postId: {
    type: String,
    required: true
  },
  isOpen: {
    type: Boolean,
    required: true
  }
});

var Philosophy = mongoose.model('Philosophy', PhilosophySchema);
module.exports.Philosophy = Philosophy;

var ShayariSchema = new mongoose.Schema({
  postId: {
    type: String,
    required: true
  },
  isOpen: {
    type: Boolean,
    required: true
  }
});

var Shayari = mongoose.model('Shayari', ShayariSchema);
module.exports.Shayari = Shayari;

var PoemSchema = new mongoose.Schema({
  postId: {
    type: String,
    required: true
  },
  isOpen: {
    type: Boolean,
    required: true
  }
});

var Poem = mongoose.model('Poem', PoemSchema);
module.exports.Poem = Poem;

var QuoteSchema = new mongoose.Schema({
  postId: {
    type: String,
    required: true
  },
  isOpen: {
    type: Boolean,
    required: true
  }
});

var Quote = mongoose.model('Quote', QuoteSchema);
module.exports.Quote = Quote;
