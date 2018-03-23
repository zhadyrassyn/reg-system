import React, { Component } from "react"
import "./overview_menu"
import {connect} from "react-redux"
import {bindActionCreators} from "redux"
import {Field, reduxForm, actionCreators, getFormValues} from "redux-form"
import MySelectComponent from "../my_select_component"
import moment from "moment"

import {
  fetchCities,
  fetchSchools,
  saveStudentGeneralInfo,
  fetchStudentGeneralInfo
} from "../../actions"

import {
  ACCESS_TYPE_SAVE,
  ACCESS_TYPE_EDIT
} from "../../constants"

//validations
const required = value => (value ? undefined : 'Required')

class StudentApp extends Component {
  constructor(props) {
    super(props)

    console.log(localStorage.getItem('token'))
  }

  componentDidMount() {
    const { fetchCities, fetchStudentGeneralInfo } = this.props
    fetchStudentGeneralInfo(
      (data) => {
        console.log('Data ', data)
        if (data.accessType === ACCESS_TYPE_SAVE) {
          fetchCities()
        } else if(data.accessType === ACCESS_TYPE_EDIT) {
          // const initialValues = {
          //   ...data
          // }
          // this.props.initialize(initialValues)
        }
      },
      () => {
        console.log('fetchStudentGeneralInfo error')
      }
    )
  }

  renderField(field) {
    const { meta: { touched, error, warning } } = field;
    let disabled = field.input.value !== undefined && field.input.value !== ''  || field.id === 'customSchool'

    if(field.type === 'date' && disabled) {
      field.input.value = moment(field.input.value).format("YYYY-MM-DD")
    }

    if(field.id === 'customSchool' && field.school) {
      disabled = true
      console.log('true)')
    }

    return (
      <div className="col">
        <label htmlFor={field.id}>{field.label}</label>
        <input type={field.type} className="form-control" name={field.name} placeholder={field.placeholder} id={field.id}
               disabled={disabled} {...field.input}
        />
        {touched && error && <span>{error}</span>}
      </div>
    )
  }

  renderSelect(field) {
    const { meta: { touched, error, warning } } = field;

    const disabled = field.input.value !== undefined && field.input.value !== -1

    return (
      <div className="col">
        <label htmlFor={field.id}>{field.label}</label>
        <MySelectComponent options={field.options} placeholder={field.placeholder} disabled={disabled} {...field.input}/>
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
    const {saveStudentGeneralInfo} = this.props
    saveStudentGeneralInfo(
      values,
      () => {
        console.log('success')
      },
      () => {
        console.log('error')
      }
    )
  }


  render() {

    const { cities, schools, studentInfo, initialValues } = this.props
    const { handleSubmit, submitting, pristine} = this.props

    const disabled = initialValues.accessType === ACCESS_TYPE_EDIT

    const actionBtnText = initialValues.accessType === ACCESS_TYPE_SAVE ? 'Save' : 'Edit'
    const actionBtnClassName = `btn mt-3 ${initialValues.accessType === ACCESS_TYPE_SAVE ? 'btn-success' : 'btn-warning'}`
    return (
      <div className="container">
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
              disabled={disabled}
            />
            <Field
              label="Middle name"
              name="middleName"
              id="middleName"
              type="text"
              placeholder="Middle name"
              component={this.renderField}
              disabled={disabled}
            />
            <Field
              label="Last name"
              name="lastName"
              id="lastName"
              type="text"
              placeholder="Last name"
              component={this.renderField}
              validate={required}
              disabled={disabled}
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
              disabled={disabled}
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
            <button className={actionBtnClassName} type="submit" disabled={submitting}>{actionBtnText}</button>
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

StudentApp = reduxForm({
  form: 'GeneralInfo',
  validate,
  enableReinitialize: true,
})(StudentApp)


StudentApp = connect(
  state => ({
    cities: state.info.cities,
    schools: state.info.schools,
    values: getFormValues('GeneralInfo')(state),
    initialValues: state.student.studentInfo
  }),
  dispatch => ({
    fetchCities: bindActionCreators(fetchCities, dispatch),
    fetchSchools: bindActionCreators(fetchSchools, dispatch),
    saveStudentGeneralInfo: bindActionCreators(saveStudentGeneralInfo, dispatch),
    fetchStudentGeneralInfo: bindActionCreators(fetchStudentGeneralInfo, dispatch)
  })
)(StudentApp)

export default StudentApp
