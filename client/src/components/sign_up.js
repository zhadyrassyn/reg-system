import React, {Component} from 'react'
import {Field, reduxForm} from "redux-form";
import {connect} from "react-redux"

class SignUp extends Component {
  renderField(field) {
    return (
      <div className="form-group">
        <label htmlFor={field.id}>{field.label}</label>
        <input type={field.type} name={field.name} className="form-control" id={field.id} placeholder={field.placeholder} {...field.input}/>
        {field.hint &&
          <small id={field.id} className="text-muted">
            {field.hintText}
          </small>
        }
      </div>
    )
  }

  onSubmit(values) {
    console.log(values)
  }

  render() {
    const { handleSubmit } = this.props;

    return (
      <div className="container">
        <form className="mx-auto mt-5 p-4" id="sign_up_form" onSubmit={handleSubmit(this.onSubmit.bind(this))}>
          <Field
            label="Email address"
            name="email"
            id="email"
            type="email"
            placeholder="Enter email"
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
            component={this.renderField}
          />

          <Field
            label="Confirm password"
            name="confirmPasword"
            id="confirmPasword"
            type="password"
            placeholder="Confirm password"
            component={this.renderField}
          />

          <button type="submit" className="btn btn-primary" id="sign_up_btn">Submit</button>
        </form>
      </div>
    )
  }
}

export default reduxForm({
  form: "SignUpForm"
})(connect(null, null)(SignUp))