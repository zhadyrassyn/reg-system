import React from 'react'

export default props => {
  const {email, onResendInvite} = props

  // asd = () => {
  //   console.log('prevent default')
  // }
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
      </form>

    </div>
  )
}
