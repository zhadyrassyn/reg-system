import React, { Component } from 'react';
import Header from './header'
import SignUp from './sign_up'
import {connect} from 'react-redux'
import { Provider as AlertProvider } from 'react-alert'
import AlertTemplate from 'react-alert-template-basic'

const alertOptions = {
  position: 'top right',
  timeout: 3000,
  offset: '40px',
  transition: 'scale',
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
          {this.props.children}
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