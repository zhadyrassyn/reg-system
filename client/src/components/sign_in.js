import React, {Component} from "react"
import {Field, reduxForm} from "redux-form"
import {signIn} from "../actions"
import {connect} from "react-redux"
import {bindActionCreators} from "redux"

class SignIn extends Component {
  constructor(props) {
    super(props)

    this.state = {
      isLoading: false
    }
  }

  renderField(field) {
    const { meta: { touched, error, warning } } = field
    const className = `form-control ${touched && error ? "has-danger" : ""}`
    return (
      <div className="form-group">
        <label htmlFor={field.id}>{field.label}</label>
        <input className={className} type={field.type} placeholder={field.placeholder} name={field.name} aria-label={field.name}
               {...field.input} />
      </div>
    )
  }


  onSubmit(values) {
    this.setState(
      {isLoading:true},
      () => {
        const {signIn} = this.props
        signIn(
          values,
          () => {
            console.log('success')
          }),
          (message) => {
            console.log('Error ', message)
          }
      })
  }

  render() {
    const {isLoading} = this.state
    const { handleSubmit, submitting} = this.props

    return (
      <div className="container">

        <form className="mx-auto mt-5 px-4 pt-4 pb-2" id="sign_in_form_body" onSubmit={handleSubmit(this.onSubmit.bind(this))}>

          <Field
            label="Email"
            type="text"
            name="email"
            placeholder="Email"
            component={this.renderField}
            id="email"
          />

          <Field
            label="Password"
            type="password"
            name="password"
            placeholder="Password"
            component={this.renderField}
            id="password"
          />

          <button type="submit" className={"btn btn-primary " + (isLoading ? "disabled" : "")} disabled={submitting}>Sign in</button>
          <div className="text-center mt-3">
            {isLoading && <span className="spinner"><i className="fa fa-spinner fa-spin fa-2x" /></span>}
          </div>

          <div className="text-center my-2">
            <p className="btn btn-link mb-0">Forgot password?</p>
            <div><small className="text-muted">Not registered? Sign up</small></div>
          </div>

        </form>
      </div>
    )
  }
}

export default reduxForm({
  form: "SignInFormBody"
})(connect(
  state => ({
    error: state.error
  }),
  dispatch => ({
    signIn: bindActionCreators(signIn, dispatch)
  })
)(SignIn))