import React, { Component } from 'react'

class SignUp extends Component {
  render() {
    return (
      <div className="container">
        <form className="mx-auto mt-5 p-4" id="sign_up_form">
          <div className="form-group">
            <label htmlFor="email">Email address</label>
            <input type="email" className="form-control" id="email" placeholder="Enter email"/>
          </div>
          <div className="form-group">
            <label htmlFor="password">Password</label>
            <input type="password" className="form-control" id="password" placeholder="Password"/>
            <small id="password" className="text-muted">
              Must be 8-20 characters long.
            </small>
          </div>
          <div className="form-group">
            <label htmlFor="passwordConfirm">Confirm password</label>
            <input type="password" className="form-control" id="passwordConfirm" placeholder="Confirm password"/>
          </div>
          <button type="submit" className="btn btn-primary" id="sign_up_btn">Submit</button>
        </form>
      </div>
    )
  }
}

export default SignUp