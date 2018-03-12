import React, { Component } from "react"
import { Link } from 'react-router'
import {Field, reduxForm} from "redux-form"
import {connect} from "react-redux"
import {bindActionCreators} from "redux"
import { withAlert } from "react-alert"
import {signIn} from "../actions";

class Header extends Component {
  renderField(field) {
    const { meta: { touched, error, warning } } = field
    return (
      <input className="form-control mr-sm-2" type={field.type} placeholder={field.placeholder} name={field.name} aria-label={field.name}
             {...field.input} />
    )
  }

  onSubmit(values) {
    console.log('Values', values)
  }

  render() {
    const { handleSubmit, submitting} = this.props;

    return (
      <nav className="navbar navbar-light bg-light justify-content-between">
        <div className="container">
          <a className="navbar-brand">Online registration system</a>
          <form className="form-inline" onSubmit={handleSubmit(this.onSubmit.bind(this))}>
            <Field
              type="email"
              name="email"
              placeholder="Email"
              component={this.renderField}
            />
            <Field
              type="password"
              name="password"
              placeholder="Password"
              component={this.renderField}
            />
            <button className="btn btn-outline-success my-2 my-sm-0" type="submit">Sign in</button>
            <small className="mx-3 form-text text-muted">Forget password?</small>
          </form>
        </div>
      </nav>
    )
  }
}

export default reduxForm({
  form: 'SignInForm',
})(connect(
  state => ({
    error: state.error
  }),
  dispatch => ({
    signIn: bindActionCreators(signIn, dispatch)
  })
)(withAlert(Header)))