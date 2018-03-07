import React, {Component} from "react"
import {connect} from "react-redux"
import {bindActionCreators} from "redux"
import {verifyEmail} from "../actions/index"


class VerificationEmail extends Component {

  componentDidMount() {
    const {token, verifyEmail} = this.props
    verifyEmail(token)
  }
  render() {
    return (
      <div>Email verification</div>
    )
  }
}

export default connect(
  (state, ownProps) => ({
    token: ownProps.match.params.token
  }),
  dispatch => ({
    verifyEmail: bindActionCreators(verifyEmail, dispatch)
  })
)(VerificationEmail)