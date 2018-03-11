import React, { Component } from 'react'

class ConfirmEmail extends Component {
  constructor(props) {
    super(props)

    this.state = {
      isLoading: true
    }
  }

  render() {
    const {email, onResendInvite, isLoading} = this.props

    return (
      <div className="container">
        <form className="mx-auto mt-5 p-5 text-center" id="confirm_email_form" onSubmit={onResendInvite}>
          <div>
            <span><i className="fas fa-envelope fa-3x"></i></span>
          </div>
          <h3 className="my-2">Confirm Email</h3>
          <small className="text-muted">{email}</small>
          <p className="my-2">Confirm your email by clicking the verification link we just sent to your inbox.</p>
          <button className="btn btn-primary mt-4">Resend invite</button>

          <div className="mt-3">
            {isLoading && <span className="spinner"><i className="fa fa-spinner fa-spin fa-2x" /></span>}
          </div>
        </form>

      </div>
    )
  }
}

export default ConfirmEmail
