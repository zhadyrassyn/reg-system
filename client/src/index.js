import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { createStore, applyMiddleware } from 'redux';
import reduxThunk from 'redux-thunk'
import "../style/styles.less"

import App from './components/app'
import VerificationEmail from './components/verification_email'
import reducers from './reducers';
import { BrowserRouter, Route, Switch } from "react-router-dom"

const createStoreWithMiddleware = applyMiddleware(reduxThunk)(createStore);
const store = createStoreWithMiddleware(reducers)

ReactDOM.render(
  <Provider store={store}>
    <BrowserRouter>
      <div>
        <Switch>
          <Route exact path="/" component={App}/>
          <Route path="/account_verification/email/:token" component={VerificationEmail}/>
        </Switch>
      </div>
    </BrowserRouter>
  </Provider>
  , document.querySelector('.root'));
