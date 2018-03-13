import React, { Component } from "react"
import { connect } from "react-redux"
import * as actions from "../actions"

class SignOut extends Component {
  componentWillMount() {
    this.props.signOut()
  }

  render() {
    return (
      <div className="container">
        Hope to see you again...
      </div>
    )
  }
}

export default connect(null, actions)(SignOut)