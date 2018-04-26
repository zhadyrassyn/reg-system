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

import {message} from "../../locale/message"

//validations
const required = value => (value ? undefined : 'Required')

class PersonalInfoForm extends Component {
  constructor(props) {
    super(props)

    this.state = {
      accessType: ACCESS_TYPE_SAVE
    }
  }

  componentDidMount() {
    const { fetchCities, fetchStudentGeneralInfo } = this.props
    fetchStudentGeneralInfo(
      (data) => {
        console.log('Data ', data)
        if (data.accessType === ACCESS_TYPE_SAVE) {
          fetchCities()
        } else if(data.accessType === ACCESS_TYPE_EDIT) {
          this.setState({accessType: ACCESS_TYPE_EDIT})
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
    console.log('Why saving?')
    saveStudentGeneralInfo(
      values,
      () => {
        console.log('Saved successfully')
        this.setState({accessType: ACCESS_TYPE_EDIT})
      },
      () => {
        console.log('error')
      }
    )
  }

  onEditClick = () => {
    const { initialValues, fetchSchools, formValues } = this.props
    this.setState({accessType: ACCESS_TYPE_SAVE_CANCELLABLE}, () => {
      this.props.fetchCities()
      this.props.fetchSchools(formValues.city.value)
    })
  }

  onCancelClicked = () => {
    const { initialValues } = this.props
    this.props.initialize(initialValues)
    this.setState({accessType: ACCESS_TYPE_EDIT})
  }


  render() {
    let { cities, schools, initialValues, lang } = this.props

    if(cities) {
      cities.map(city => {
        return {}
      })
    }

    console.log('cities ', cities)
    // const selectBoxCities = cities.m
    const { handleSubmit, submitting} = this.props

    const accessType = this.state.accessType

    return (
      <div className="container-fluid">
        <form onSubmit={handleSubmit(this.onSubmit.bind(this))}>
          <div className="form-row">
            <Field
              label={message.first_name[lang]}
              name="firstName"
              id="firstName"
              type="text"
              placeholder={message.first_name[lang]}
              component={this.renderField}
              validate={required}
              accessType={accessType}
            />
            <Field
              label={message.patronymic[lang]}
              name="middleName"
              id="middleName"
              type="text"
              placeholder={message.patronymic[lang]}
              component={this.renderField}
              accessType={accessType}
            />
            <Field
              label={message.last_name[lang]}
              name="lastName"
              id="lastName"
              type="text"
              placeholder={message.last_name[lang]}
              component={this.renderField}
              validate={required}
              accessType={accessType}
            />
          </div>
          <div className="form-row mt-3">
            <Field
              label={message.birthDate[lang]}
              name="birthDate"
              id="birthDate"
              type="date"
              component={this.renderField}
              validate={required}
              accessType={accessType}
            />
          </div>
          <div className="form-row mt-3">
            <Field
              label={message.city[lang]}
              name="city"
              id="city"
              options={cities}
              component={this.renderSelect}
              onChange={this.handleCityChange}
              validate={required}
              placeholder={message.city[lang]}
              accessType={accessType}
            />
          </div>
          <div className="form-row mt-3">
            <Field
              label={message.school[lang]}
              name="school"
              id="school"
              options={schools}
              component={this.renderSelect}
              placeholder={message.school[lang]}
              accessType={accessType}
            />
            <Field
              label={message.customSchool[lang]}
              id="customSchool"
              name="customSchool"
              type="text"
              component={this.renderField}
              placeholder={message.customSchool[lang]}
              accessType={accessType}
            />
          </div>
          <div className="col text-right">
            {accessType === ACCESS_TYPE_SAVE || accessType === ACCESS_TYPE_SAVE_CANCELLABLE &&
              <button className={"btn mt-3 " + (accessType === ACCESS_TYPE_SAVE_CANCELLABLE ? 'btn-danger' : 'btn-success')} type="submit" disabled={submitting}>{message.save[lang]}</button>}
            {accessType === ACCESS_TYPE_EDIT && <button className="btn mt-3 btn-warning" type="button" onClick={this.onEditClick}>{message.edit[lang]}</button>}
            {accessType === ACCESS_TYPE_SAVE_CANCELLABLE && <button className="btn mt-3 ml-3 btn-success" type="button" onClick={this.onCancelClicked}>{message.cancel[lang]}</button>}
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

PersonalInfoForm = reduxForm({
  form: 'GeneralInfo',
  validate,
  enableReinitialize: true,
})(PersonalInfoForm)

function refactorCities(cities) {
  if(cities) {
    cities = cities.map(city => ({
      ...city,
      value: city.id,
      label: city.nameRu
    }))
  }

  return cities
}

function refactorSchools(schools) {
  if(schools) {
    schools = schools.map(school => ({
      ...school,
      value: school.id,
      label: school.nameRu
    }))
  }

  return schools
}

function refactorGeneralInfo(studentInfo) {
  if(studentInfo && studentInfo.city && studentInfo.school) {
    studentInfo = {
      ...studentInfo,
      city: {
        ...studentInfo.city,
        value: studentInfo.city.id,
        label: studentInfo.city.nameRu
      },
      school: {
        ...studentInfo.school,
        value: studentInfo.school.id,
        label: studentInfo.school.nameRu
      }
    }
  }
  console.log('student info ', studentInfo)
  return studentInfo
}

export default connect(
  state => ({
    lang: state.lang,
    cities: refactorCities(state.info.cities),
    schools: refactorSchools(state.info.schools),
    formValues: getFormValues('GeneralInfo')(state),
    initialValues: refactorGeneralInfo(state.student.studentInfo)
  }),
  dispatch => ({
    fetchCities: bindActionCreators(fetchCities, dispatch),
    fetchSchools: bindActionCreators(fetchSchools, dispatch),
    saveStudentGeneralInfo: bindActionCreators(saveStudentGeneralInfo, dispatch),
    fetchStudentGeneralInfo: bindActionCreators(fetchStudentGeneralInfo, dispatch),
    changeFieldValue: bindActionCreators(changeFieldValue, dispatch)
  })
)(PersonalInfoForm)