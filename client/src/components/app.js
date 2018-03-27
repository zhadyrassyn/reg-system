import React, { Component } from "react"
import Header from "./header"
import { connect } from "react-redux"

import StudentApp from "./student"

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
    const { authenticated } = this.props

    return (
      <div>
        <Header/>
          {this.props.children}

          { authenticated && <StudentApp/> }
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