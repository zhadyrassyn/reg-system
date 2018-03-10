import React, {Component} from "react"
import {Field, reduxForm} from "redux-form"
import {connect} from "react-redux"
import {bindActionCreators} from "redux"
import {singUp} from "../actions/index"

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
      isLoading: false
    }

  }
  renderField(field) {
    const { meta: { touched, error, warning } } = field;
    const className = `form-group ${touched && error ? "has-danger" : ""}`;

    return (
      <div className={className}>
        <label htmlFor={field.id}>{field.label}</label>
        <input type={field.type} name={field.name} className="form-control" id={field.id} placeholder={field.placeholder} {...field.input}/>
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
    this.setState({isLoading: true})

    const {signUp} = this.props
    signUp(
      values,
      () => {
        this.setState({isLoading: false})
      },
      (message) => {
        console.log(message)
      })
  }

  render() {
    const { handleSubmit, submitting } = this.props;
    console.log('Submittinh ', submitting)
    const { isLoading } = this.state
    console.log(isLoading)
    return (
      <div className="container">
        <form className="mx-auto mt-5 px-4 pt-4 pb-2" id="sign_up_form" onSubmit={handleSubmit(this.onSubmit.bind(this))}>
          <Field
            label="Email address"
            name="email"
            id="email"
            type="email"
            placeholder="Enter email"
            validate={[required, email]}
            component={this.renderField}
          />

          <Field
            label="Password"
            name="password"
            id="password"
            type="password"
            placeholder="Password"
            hint="true"
            hintText="Must be 8-20 characters long."
            validate={[required, minLength8]}
            component={this.renderField}
          />

          <Field
            label="Confirm password"
            name="confirmPasword"
            id="confirmPasword"
            type="password"
            placeholder="Confirm password"
            validate={required}
            component={this.renderField}
          />

          <button type="submit" className={"btn btn-primary " + (isLoading ? "disabled" : "")} id="sign_up_btn" disabled={submitting}>Submit</button>
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
    error: state.error
  }),
  dispatch => ({
    signUp: bindActionCreators(singUp, dispatch)
  })
)(SignUp))
