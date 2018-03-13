import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';

import { createStore, applyMiddleware } from 'redux';
import reduxThunk from 'redux-thunk'
import { createLogger } from "redux-logger"
import "../style/styles.less"

import App from './components/app'
import VerificationEmail from './components/verification_email'
import SignIn from "./components/sign_in"
import SignUp from "./components/sign_up"
import Auth from "./components/require_auth"
import { Provider as AlertProvider } from 'react-alert'
import AlertTemplate from 'react-alert-template-basic'


import reducers from './reducers';
import { Router, Route, IndexRedirect, browserHistory} from "react-router"

const middlewares = [ reduxThunk ];
if(process.env.NODE_ENV !== 'production') {
  middlewares.push(createLogger());
}

const createStoreWithMiddleware = applyMiddleware(...middlewares)(createStore);
const store = createStoreWithMiddleware(reducers)

const alertOptions = {
  position: 'top right',
  timeout: 3000,
  offset: '40px',
  transition: 'scale',
}

ReactDOM.render(
  <Provider store={store}>
    <Router history={browserHistory}>
      <Route path="/account_verification/email/:token" component={VerificationEmail}/>
      {/*<AlertProvider template={AlertTemplate} {...alertOptions}>*/}
        <Route path="/" component={Auth(App)}>
          <Route path="/signin" component={SignIn}/>
          <Route path="/registration" component={SignUp}/>
        </Route>
      {/*</AlertProvider>*/}
    </Router>
  </Provider>
  , document.querySelector('.root'));
