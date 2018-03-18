import React, { Component } from "react"
import "./overview_menu"
import OverviewMenu from "./overview_menu"
import Select from "react-select"
import {connect} from "react-redux"
import {bindActionCreators} from "redux"
import {Field, reduxForm, actionCreators, getFormNames, getFormValues} from "redux-form"
import MySelectComponent from "../my_select_component"

import {
  fetchCities,
  fetchSchools
} from "../../actions"

//validations
const required = value => (value ? undefined : 'Required')

class StudentApp extends Component {
  constructor(props) {
    super(props)

    console.log(localStorage.getItem('token'))
  }

  componentDidMount() {
    const { fetchCities } = this.props
    fetchCities()
  }

  renderField(field) {
    const { meta: { touched, error, warning } } = field;

    return (
      <div className="col">
        <label htmlFor={field.id}>{field.label}</label>
        <input type={field.type} className="form-control" name={field.name} placeholder={field.placeholder} id={field.id} {...field.input}/>
        {touched && error && <span>{error}</span>}
      </div>
    )
  }

  renderSelect(field) {
    const { meta: { touched, error, warning } } = field;

    return (
      <div className="col">
        <label htmlFor={field.id}>{field.label}</label>
        <MySelectComponent {...field.input} options={field.options} placeholder={field.placeholder}/>
        {touched && error && <span>{error}</span>}
      </div>
    )
  }

  handleCityChange = (city) => {
      const {fetchSchools} = this.props
      fetchSchools(city.value,
        () => {
        this.props.values['school'] = {}
        actionCreators.change('school')
        },
        () => {
          console.log('error on fetching schools')
        })

    }

  onSubmit(values) {
    console.log('Values ', values)
  }


  render() {

    const { cities, schools } = this.props
    const { handleSubmit, submitting } = this.props

    return (
      <div className="container">
        <OverviewMenu/>
        <form onSubmit={handleSubmit(this.onSubmit.bind(this))}>
          <div className="form-row">
            <Field
              label="First name"
              name="firstName"
              id="firstName"
              type="text"
              placeholder="First name"
              component={this.renderField}
              validate={required}
            />
            <Field
              label="Middle name"
              name="middleName"
              id="middleName"
              type="text"
              placeholder="Middle name"
              component={this.renderField}
            />
            <Field
              label="Last name"
              name="lastName"
              id="lastName"
              type="text"
              placeholder="Last name"
              component={this.renderField}
              validate={required}
            />
          </div>
          <div className="form-row mt-3">
            <Field
              label="Date of birth"
              name="birthDate"
              id="birthDate"
              type="date"
              component={this.renderField}
              validate={required}
            />
          </div>
          <div className="form-row mt-3">
            <Field
              label="City"
              name="city"
              id="city"
              options={cities}
              component={this.renderSelect}
              onChange={this.handleCityChange}
              validate={required}
              placeholder="Choose your city"
            />
          </div>
          <div className="form-row mt-3">
            <Field
              label="School"
              name="school"
              id="school"
              options={schools}
              component={this.renderSelect}
              placeholder="Choose your school"
            />
            <Field
              label="Not finding your school? Write down"
              id="customSchool"
              name="customSchool"
              type="text"
              component={this.renderField}
              placeholder="..."
            />
          </div>
          <div className="col text-right">
            <button className="btn btn-success mt-3" type="submit" disabled={submitting}>Save</button>
          </div>
        </form>
      </div>
    )
  }
}

const validate = (values) => {
  const errors = {}

  if(!values.school && !values.customSchool) {
    errors.school = "Required"
  }
  return errors
}

StudentApp = connect(
  state => ({
    cities: state.info.cities,
    schools: state.info.schools,
    values: getFormValues('GeneralInfo')(state)
  }),
  dispatch => ({
    fetchCities: bindActionCreators(fetchCities, dispatch),
    fetchSchools: bindActionCreators(fetchSchools, dispatch),
  })
)(StudentApp)

export default reduxForm({
  form: 'GeneralInfo',
  validate
})(StudentApp)