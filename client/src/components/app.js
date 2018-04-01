import React, { Component } from "react"
import Header from "./header"
import { connect } from "react-redux"

import StudentApp from "./student"
import ModeratorApp from "./moderator"

import jwtDecode from "jwt-decode"

import {
  ROLE_MODERATOR,
  ROLE_USER
} from "../constants"

class App extends Component {

  constructor(props) {
    super(props)
  }

  render() {
    const { authenticated } = this.props

    let user = ROLE_USER;

    const token = localStorage.getItem('token')
    if(token) {
      const decoded = jwtDecode(token)

      if(decoded.scope.toUpperCase() === ROLE_MODERATOR) {
        user = ROLE_MODERATOR
      }
    }

    return (
      <div>
        <Header/>
          {this.props.children}

          { authenticated && user === ROLE_USER && <StudentApp/> }

          { authenticated && user === ROLE_MODERATOR && <ModeratorApp/> }
      </div>
    )
  }
}

export default (connect(
  state => ({
    authenticated: state.auth.authenticated
  }),
  dispatch => ({
  })
)(App))