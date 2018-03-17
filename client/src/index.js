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
import SignOut from "./components/sign_out"
import Auth from "./components/require_auth"

//student
import StudentApp from "./components/student/app"
import OverviewMenu from "./components/student/overview_menu"

//moderator
import ModeratorApp from "./components/moderator/app"

import jwtDecode from "jwt-decode"

import {
  SIGN_IN_SUCCESS,
  SIGN_IN_FAILURE
} from "./actions/types"

import {
  ROLE_USER,
  ROLE_MODERATOR
} from "./constants"

import reducers from './reducers';
import { Router, Route, IndexRedirect, browserHistory} from "react-router"

const middlewares = [ reduxThunk ];
if(process.env.NODE_ENV !== 'production') {
  middlewares.push(createLogger());
}

const createStoreWithMiddleware = applyMiddleware(...middlewares)(createStore);
const store = createStoreWithMiddleware(reducers)

let token = localStorage.getItem('token')
if(token) {
  store.dispatch({ type: SIGN_IN_SUCCESS })
}

const alertOptions = {
  position: 'top right',
  timeout: 3000,
  offset: '40px',
  transition: 'scale',
}

const requireModeratorRole = (nextState, replace) => {
  console.log('Called, yo')
  token = localStorage.getItem('token')
  if(!token) {
    store.dispatch({type: SIGN_IN_FAILURE})
  } else {
    const decoded = jwtDecode(token)
    if(decoded.iat > decoded.exp || decoded.scope.toUpperCase() !== ROLE_MODERATOR) {
      browserHistory.push('/')
    }
  }
}

ReactDOM.render(
  <Provider store={store}>
    <Router history={browserHistory}>
      <Route path="/account_verification/email/:token" component={VerificationEmail}/>
      {/*<AlertProvider template={AlertTemplate} {...alertOptions}>*/}
        <Route path="/" component={Auth(App)}>
          <Route path="/signin" component={SignIn}/>
          <Route path="/signout" component={SignOut}/>
          <Route path="/registration" component={SignUp}/>
          <Route path="/home" component={StudentApp}/>
          <Route path="/moderator" component={ModeratorApp} onEnter={requireModeratorRole}/>
        </Route>
      {/*</AlertProvider>*/}
    </Router>
  </Provider>
  , document.querySelector('.root'));
