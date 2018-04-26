import React, {Component} from "react"
import {Link} from 'react-router'
import {Field, reduxForm} from "redux-form"
import {connect} from "react-redux"
import {bindActionCreators} from "redux"
import {signIn, changeLang} from "../actions"
import {message} from "../locale/message"

class Header extends Component {
  renderField(field) {
    const {meta: {touched, error, warning}} = field
    const className = `form-control mr-sm-2 ${touched && error ? "has-danger" : ""}`
    return (
      <div>
        <input className={className} type={field.type} placeholder={field.placeholder} name={field.name}
               aria-label={field.name}
               {...field.input} />
      </div>
    )
  }

  onSubmit(values) {
    const {signIn} = this.props
    signIn(
      values,
      () => {
        console.log('success')
      },
      () => {
        console.log('error')
      }
    )
  }

  changeLang = (e) => {
    e.preventDefault()
    this.props.changeLang(e.target.name)
  }

  render() {
    const {handleSubmit, submitting, signInFailed, authenticated, lang} = this.props

    return (
      <nav className="navbar navbar-light bg-light justify-content-between">
        <div className="container-fluid">
          <a className="navbar-brand">{message.header_title[lang]}</a>

          <div className="nav">
            <div className="nav-item dropdown">
              <button className="dropbtn">{message.lang_title[lang]} <i className="fas fa-chevron-down"></i></button>
              <div className="dropdown-content">
                <a href="#" onClick={this.changeLang} name="ru">РУ</a>
                <a href="#" onClick={this.changeLang} name="en">EN</a>
                <a href="#" onClick={this.changeLang} name="kk">ҚАЗ</a>
              </div>
            </div>

            {!authenticated && !signInFailed &&
            <form className="nav-item form-inline" onSubmit={handleSubmit(this.onSubmit.bind(this))}>
              <Field
                type="text"
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
              <button className="btn btn-outline-success my-2 my-sm-0" type="submit" disabled={submitting}>Sign in
              </button>
              <small className="mx-3 form-text text-muted">Forget password?</small>
            </form>
            }
            {!authenticated && signInFailed &&
            <form className="form-inline">
              <Link to="/signin" className="nav-link btn btn-outline-success my-2 my-sm-0 mr-2">Sign in</Link>
              <Link to="/registration" className="nav-link btn btn-outline-primary my-2 my-sm-0">Sign up</Link>
            </form>
            }
            {authenticated &&
            <Link to="/signout" className="nav-link btn btn-outline-danger my-2 my-sm-0 width120">Sign out</Link>

            }
          </div>

        </div>
      </nav>
    )
  }
}

const validate = (values) => {
  const errors = {}
  if (!values.email) {
    errors.email = "Email required"
  }
  if (values.email && !/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(values.email)) {
    errors.email = "Invalid email address"
  }
  if (!values.password) {
    errors.password = "Password required"
  }
  return errors
}

export default reduxForm({
  form: 'SignInForm',
  validate
})(connect(
  state => ({
    error: state.error,
    signInFailed: state.auth.signInFailed,
    authenticated: state.auth.authenticated,
    lang: state.lang
  }),
  dispatch => ({
    signIn: bindActionCreators(signIn, dispatch),
    changeLang: bindActionCreators(changeLang, dispatch)
  })
)(Header))