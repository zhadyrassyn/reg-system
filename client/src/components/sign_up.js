import React, {Component} from "react"
import {Field, reduxForm} from "redux-form"
import {connect} from "react-redux"
import {bindActionCreators} from "redux"
import {singUp, resendVerificationEmail} from "../actions/index"
import ConfirmEmail from "./confirm_email"
import {message} from "../locale/message";

//validations
const required = value => (value ? undefined : 'Required')
const minLength = min => value =>
  value && value.length < min ? `Must be ${min} characters or more` : undefined
const minLength8 = minLength(8)
const email = value =>
  value && !/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(value)
    ? 'Invalid email address'
    : undefined

class SignUp extends Component {

  constructor(props) {
    super(props)

    this.state = {
      isLoading: false,
      signedUp: false,
      userEmail: 'test@test.com',
      emailConfirmationLoader: false
    }
  }

  renderField(field) {
    const { meta: { touched, error, warning } } = field;
    const className = `form-control ${touched && error ? "has-danger" : ""}`

    return (
      <div className="form-group">
        <label htmlFor={field.id}>{field.label}</label>
        <input type={field.type} name={field.name} className={className} id={field.id} placeholder={field.placeholder} {...field.input}/>
        {field.hint && !touched &&
          <small id={field.id} className="text-muted">
            {field.hintText}
          </small>
        }
        <div className="text-help">
          <small className="form-text text-danger">
            {touched &&
            ((error && <span>{error}</span>) ||
              (warning && <span>{warning}</span>))}
          </small>

        </div>
      </div>
    )
  }

  onSubmit(values) {
    this.setState({
      isLoading: true
    })

    const {signUp} = this.props

    signUp(
      values,
      () => {
        this.setState({
          isLoading: false,
          signedUp: true,
          userEmail: values.email
        })
      },
      (message) => {
        console.log(message)
      })
  }

  onResendInvite = (e) => {
    e.preventDefault()

    this.setState({emailConfirmationLoader: true}, () => {
      const {resendVerificationEmail} = this.props
      const {userEmail} = this.state

      resendVerificationEmail(
        userEmail,
        () => {
          this.setState({emailConfirmationLoader: false})
        },
        () => {
          this.setState({emailConfirmationLoader: false})
        }
      )
    })
  }

  render() {
    const { handleSubmit, submitting, lang} = this.props;
    const { isLoading, signedUp, userEmail, emailConfirmationLoader } = this.state

    if(signedUp) {
      return <ConfirmEmail email={userEmail} onResendInvite={this.onResendInvite} isLoading={emailConfirmationLoader}/>
    }

    return (
      <div className="container">

        <form className="mx-auto mt-5 px-4 pt-4 pb-2" id="sign_up_form" onSubmit={handleSubmit(this.onSubmit.bind(this))}>
          <Field
            label={message.email[lang]}
            name="email"
            id="email"
            type="text"
            placeholder={message.enter_email[lang]}
            validate={[required, email]}
            component={this.renderField}
          />

          <Field
            label={message.password[lang]}
            name="password"
            id="password"
            type="password"
            placeholder={message.enter_password[lang]}
            hint="true"
            hintText={message.password_length_rule[lang]}
            validate={[required, minLength8]}
            component={this.renderField}
          />

          <Field
            label={message.confirm_password[lang]}
            name="confirmPasword"
            id="confirmPasword"
            type="password"
            placeholder={message.confirm_password[lang]}
            validate={required}
            component={this.renderField}
          />

          <button type="submit" className={"btn btn-primary " + (isLoading ? "disabled" : "")} id="sign_up_btn" disabled={submitting}>{message.sign_up[lang]}</button>
          <div className="text-center mt-3">
            {isLoading && <span className="spinner"><i className="fa fa-spinner fa-spin fa-2x" /></span>}
          </div>

        </form>
      </div>
    )
  }
}

const validate = (values) => {
  const errors = {};
  if(!values.confirmPasword || (values.password && values.confirmPasword && values.password !== values.confirmPasword)) {
    errors.confirmPasword = 'Passwords mismtach'
  }
  return errors;
}


export default reduxForm({
  form: "SignUpForm",
  validate
})(connect(
  state => ({
    error: state.error,
    lang: state.lang
  }),
  dispatch => ({
    signUp: bindActionCreators(singUp, dispatch),
    resendVerificationEmail: bindActionCreators(resendVerificationEmail, dispatch)
  })
)(SignUp))
