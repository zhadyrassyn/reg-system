import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';

import { createStore, applyMiddleware } from 'redux';
import reduxThunk from 'redux-thunk'
import { createLogger } from "redux-logger"
import "../style/styles.less"

import App from './components/app'
import VerificationEmail from './components/verification_email'
import reducers from './reducers';
import { Router, Route, IndexRoute, browserHistory, Switch } from "react-router"

const middlewares = [ reduxThunk ];
if(process.env.NODE_ENV !== 'production') {
  middlewares.push(createLogger());
}

const createStoreWithMiddleware = applyMiddleware(...middlewares)(createStore);
const store = createStoreWithMiddleware(reducers)

ReactDOM.render(
  <Provider store={store}>
    <Router history={browserHistory}>
      <Route path="/account_verification/email/:token" component={VerificationEmail}/>
      <Route path="/" component={App}/>
    </Router>
  </Provider>
  , document.querySelector('.root'));
