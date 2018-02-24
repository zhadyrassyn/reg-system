import React, { Component } from 'react'
import { Link } from 'react-router'

class Header extends Component {
  render() {
    return (
      <nav className="navbar navbar-light bg-light justify-content-between">
        <div className="container">
          <a className="navbar-brand">Online registration system</a>
          <form className="form-inline">
            <input className="form-control mr-sm-2" type="email" placeholder="Email" aria-label="Email"/>
            <input className="form-control mr-sm-2" type="password" placeholder="Password" aria-label="Password"/>
            <button className="btn btn-outline-success my-2 my-sm-0" type="submit">Sign in</button>
            <small className="mx-3 form-text text-muted">Forget password?</small>
          </form>
        </div>
      </nav>
    )
  }
}

export default Header