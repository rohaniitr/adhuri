var express = require('express');
var bodyParser = require('body-parser');
var multer = require('multer');
var session = require('express-session');
var bcrypt = require('bcrypt-nodejs');
var app = express();
var Users = require('./models/users');
var authController = require('./routes/auth-controller');
var userController = require('./routes/user-controller');
var openPostController = require('./routes/open-post-controller');
var closePostController = require('./routes/close-post-controller');
var suggestionController = require('./routes/suggestion-controller');
var tokenController = require('./routes/token-controller');

// for parsing application/json
app.use(bodyParser.json());
// for parsing application/xwww-
app.use(bodyParser.urlencoded());
//Make the folder public for profile pictures
app.use(express.static('public/images'));

app.use('/users',authController); //Done
app.use('/',tokenController); //Required here //Done
app.use('/users',userController); //Done
app.use('/openPost',openPostController);
app.use('/closePost',closePostController);
app.use('/suggestion',suggestionController);



app.listen(3000);
console.log('Starting Server!');
