import React, { Component } from "react"
import "./overview_menu"
import {connect} from "react-redux"
import {bindActionCreators} from "redux"
import {Field, reduxForm, actionCreators, getFormValues, change as changeFieldValue} from "redux-form"
import MySelectComponent from "../my_select_component"

import {
  fetchCities,
  fetchSchools,
  saveStudentGeneralInfo,
  fetchStudentGeneralInfo
} from "../../actions"

import {
  ACCESS_TYPE_SAVE,
  ACCESS_TYPE_EDIT,
  ACCESS_TYPE_SAVE_CANCELLABLE
} from "../../constants"

//validations
const required = value => (value ? undefined : 'Required')

class StudentApp extends Component {
  constructor(props) {
    super(props)

  }

  componentDidMount() {
    const { fetchCities, fetchStudentGeneralInfo } = this.props
    fetchStudentGeneralInfo(
      (data) => {
        console.log('Data ', data)
        if (data.accessType === ACCESS_TYPE_SAVE) {
          fetchCities()
        } else if(data.accessType === ACCESS_TYPE_EDIT) {
        }
      },
      () => {
        console.log('fetchStudentGeneralInfo error')
      }
    )
  }

  renderField(field) {
    const { meta: { touched, error, warning } } = field;
    let disabled = field.accessType === ACCESS_TYPE_EDIT

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

    const disabled = field.accessType === ACCESS_TYPE_EDIT

    return (
      <div className="col">
        <label htmlFor={field.id}>{field.label}</label>
        <MySelectComponent options={field.options} placeholder={field.placeholder} disabled={disabled} {...field.input}/>
        {touched && error && <span>{error}</span>}
      </div>
    )
  }

  handleCityChange = (city) => {
      const {fetchSchools, initialValues} = this.props
      fetchSchools(city.value,
        () => {
          this.props.changeFieldValue('GeneralInfo', 'school', null)
        },
        () => {
          console.log('error')
        })

    }

  onSubmit(values) {
    const {saveStudentGeneralInfo, initialValues} = this.props
    saveStudentGeneralInfo(
      values,
      () => {
        console.log('true')
      },
      () => {
        console.log('error')
      }
    )
  }

  onEditClick = () => {
    const { initialValues, fetchSchools, formValues } = this.props
    initialValues.accessType = ACCESS_TYPE_SAVE_CANCELLABLE
    this.props.fetchCities()
    this.props.fetchSchools(formValues.city.value)
  }


  render() {

    const { cities, schools, initialValues } = this.props
    const { handleSubmit, submitting} = this.props

    const accessType = initialValues.accessType

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
              accessType={initialValues.accessType}
            />
            <Field
              label="Middle name"
              name="middleName"
              id="middleName"
              type="text"
              placeholder="Middle name"
              component={this.renderField}
              accessType={initialValues.accessType}
            />
            <Field
              label="Last name"
              name="lastName"
              id="lastName"
              type="text"
              placeholder="Last name"
              component={this.renderField}
              validate={required}
              accessType={initialValues.accessType}
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
              accessType={initialValues.accessType}
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
              accessType={initialValues.accessType}
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
              accessType={initialValues.accessType}
            />
            <Field
              label="Not finding your school? Write down"
              id="customSchool"
              name="customSchool"
              type="text"
              component={this.renderField}
              placeholder="..."
              accessType={initialValues.accessType}
            />
          </div>
          <div className="col text-right">
            {accessType === ACCESS_TYPE_SAVE || accessType === ACCESS_TYPE_SAVE_CANCELLABLE &&
              <button className={"btn mt-3 " + (accessType === ACCESS_TYPE_SAVE_CANCELLABLE ? 'btn-danger' : 'btn-success')} type="submit" disabled={submitting}>Save</button>}
            {accessType === ACCESS_TYPE_EDIT && <button className="btn mt-3 btn-warning" onClick={this.onEditClick}>Edit</button>}
            {accessType === ACCESS_TYPE_SAVE_CANCELLABLE && <button className="btn mt-3 ml-3 btn-success">Cancel</button>}
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
    formValues: getFormValues('GeneralInfo')(state),
    initialValues: state.student.studentInfo
  }),
  dispatch => ({
    fetchCities: bindActionCreators(fetchCities, dispatch),
    fetchSchools: bindActionCreators(fetchSchools, dispatch),
    saveStudentGeneralInfo: bindActionCreators(saveStudentGeneralInfo, dispatch),
    fetchStudentGeneralInfo: bindActionCreators(fetchStudentGeneralInfo, dispatch),
    changeFieldValue: bindActionCreators(changeFieldValue, dispatch)
  })
)(StudentApp)

export default StudentApp
