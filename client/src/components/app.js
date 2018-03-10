import React, { Component } from 'react';
import Header from './header'
import SignUp from './sign_up'
import {connect} from 'react-redux'

class App extends Component {

  constructor(props) {
    super(props)
  }

  render() {
    return (
      <div>
        <Header/>
        <SignUp/>
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