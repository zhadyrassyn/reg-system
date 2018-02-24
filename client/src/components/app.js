import React, { Component } from 'react';
import Header from './header'
import SignUp from './sign_up'

export default class App extends Component {
  render() {
    return (
      <div>
        <Header/>
        <SignUp/>
      </div>
    )
  }
}
