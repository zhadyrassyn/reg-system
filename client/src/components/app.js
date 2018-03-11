import React, { Component } from 'react';
import Header from './header'
import SignUp from './sign_up'
import {connect} from 'react-redux'
import { Provider as AlertProvider } from 'react-alert'
import AlertTemplate from 'react-alert-template-basic'


const alertOptions = {
  position: 'bottom center',
  timeout: 5000,
  offset: '30px',
  transition: 'scale'
}

class App extends Component {

  constructor(props) {
    super(props)
  }

  render() {
    return (
      <div>
        <AlertProvider template={AlertTemplate} {...alertOptions}>
        <Header/>
        <SignUp/>
        </AlertProvider>
      </div>
    )
  }
}

export default (connect(
  state => ({
  }),
  dispatch => ({
  })
)(App))