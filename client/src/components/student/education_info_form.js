import React, {Component} from "react"
import {connect} from "react-redux"
import {message} from "../../locale/message"

import {bindActionCreators} from "redux"
import {Field, reduxForm, actionCreators, getFormValues, change as changeFieldValue} from "redux-form"

import _ from "lodash"
import MySelectComponent from "./my_select_component"

import {
  ACCESS_TYPE_EDIT,
  ACCESS_TYPE_SAVE_CANCELLABLE,
  ACCESS_TYPE_SAVE
} from "../../constants"

import {
  fetchAreas,
  fetchCities,
  fetchSchools,
  fetchFaculties,
  fetchSpecialities
} from "../../actions"

class EducationInfoForm extends Component {

  constructor(props) {
    super(props)

    this.state = {
      accessType: ACCESS_TYPE_SAVE
    }
  }

  componentDidMount() {
    const {fetchAreas, fetchFaculties} = this.props
    fetchAreas()
    fetchFaculties(
      () => {
        console.log('success on fetching faculties')
      },
      () => {
        console.log('fail on fetching faculties')
      }
    )
  }

  renderField(field) {
    const {meta: {touched, error, warning}} = field;
    let disabled = field.accessType === ACCESS_TYPE_EDIT

    return (
      <div className="col">
        <label htmlFor={field.id}>{field.label}</label>
        <input type={field.type} className="form-control" name={field.name} placeholder={field.placeholder}
               id={field.id} disabled={disabled} {...field.input}/>

        {touched && error && <span>{error}</span>}
      </div>
    )
  }

  renderSelect(field) {
    const {meta: {touched, error, warning}, options} = field
    let disabled = field.accessType === ACCESS_TYPE_EDIT

    return (
      <div className="col">
        <label className="d-block">{field.label}</label>
        <select {...field.input} className="form-control" disabled={disabled}>
          <option value="">Выбрать</option>
          {options && Object.keys(options).map((key, index) => (
            <option value={options[key].value} key={key}>{options[key].label}</option>
          ))}
        </select>
        {touched && error && <span className="d-block">{error}</span>}
      </div>
    )
  }

  renderSelect(field) {
    const {meta: {touched, error, warning}} = field;

    const disabled = field.accessType === ACCESS_TYPE_EDIT

    return (
      <div className="col">
        <label htmlFor={field.id}>{field.label}</label>
        <MySelectComponent options={field.options} placeholder={field.placeholder}
                           disabled={disabled} {...field.input}/>
        {touched && error && <span>{error}</span>}
      </div>
    )
  }

  handleAreaChange = (item) => {
    if (item && item.id) {
      const {fetchCities, initialValues, changeFieldValue} = this.props
      fetchCities(item.id,
        () => {
          console.log('success on fetching cities')
          changeFieldValue('EducationInfoForm', 'city', null)
        },
        () => {
          console.log('error on fetching cities')
        })
    }
  }

  handleCityChange = (item) => {
    if (item && item.id) {
      const {fetchSchools, changeFieldValue} = this.props
      fetchSchools(item.areaId, item.id,
        () => {
          console.log('success on fetching schools')
          changeFieldValue('EducationInfoForm', 'school', null)
        },
        () => {
          console.log('error on fetching schools')
        })
    }
  }

  handleFacultyChange = (item) => {
    if (item && item.id) {
      const {fetchSpecialities, changeFieldValue} = this.props
      fetchSpecialities(item.id,
        () => {
          console.log('success on fetching specialities')
          changeFieldValue('EducationInfoForm', 'speciality', null)
        },
        () => {
          console.log('error on fetching specialities')
        })
    }

  }

  onSubmit(values) {
    console.log('values ', values)
  }

  refactor = (collection, lang) => {
    return _.map(collection).map(item => {
      const label = lang === 'ru' ? item.nameRu : lang === 'en' ? item.nameEn : item.nameKkk
      return {
        ...item,
        value: item.id,
        label
      }
    })
  }

  render() {
    let {lang, areas, cities, schools, handleSubmit, submitting, faculties, specialities} = this.props
    const {accessType} = this.state

    if (areas) {
      areas = this.refactor(areas, lang)
    }

    if (cities) {
      cities = this.refactor(cities, lang)
    }

    if (schools) {
      schools = this.refactor(schools, lang)
    }

    if (faculties) {
      faculties = this.refactor(faculties, lang)
    }

    if (specialities) {
      specialities = this.refactor(specialities, lang)
    }

    console.log('faculties ', faculties)

    return (
      <div className="container-fluid">
        <form onSubmit={handleSubmit(this.onSubmit.bind(this))} className="my-2">
          <div className="form-row">
            <Field
              label={message.area[lang]}
              name="educationArea"
              id="educationArea"
              options={areas}
              component={this.renderSelect}
              onChange={this.handleAreaChange}
              // validate={required}
              placeholder={message.area[lang]}
              accessType={accessType}
            />
            <Field
              label={message.city_village[lang]}
              name="city"
              id="city"
              options={cities}
              component={this.renderSelect}
              onChange={this.handleCityChange}
              // validate={required}
              placeholder={message.city_village[lang]}
              accessType={accessType}
            />

            <Field
              label={message.another_cityVillage[lang]}
              name="another_cityVillage"
              id="another_cityVillage"
              type="text"
              placeholder={message.another_cityVillage[lang]}
              component={this.renderField}
              // validate={required}
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
              // validate={required}
              placeholder={message.school[lang]}
              accessType={accessType}
            />

            <Field
              label={message.customSchool[lang]}
              name="customSchool"
              id="customSchool"
              type="text"
              placeholder={message.customSchool[lang]}
              component={this.renderField}
              // validate={required}
              accessType={accessType}
            />

            <Field
              label={message.school_finish[lang]}
              name="school_finish"
              id="school_finish"
              type="date"
              placeholder={message.school_finish[lang]}
              component={this.renderField}
              // validate={required}
              accessType={accessType}
            />
          </div>

          <div className="form-row mt-3">
            <Field
              label={message.ent_certificate_number[lang]}
              name="ent_certificate_number"
              id="ent_certificate_number"
              type="text"
              placeholder={message.ent_certificate_number[lang]}
              component={this.renderField}
              // validate={required}
              accessType={accessType}
            />
            <Field
              label={message.ent_amount[lang]}
              name="ent_amount"
              id="ent_amount"
              type="text"
              placeholder={message.ent_amount[lang]}
              component={this.renderField}
              // validate={required}
              accessType={accessType}
            />
            <Field
              label={message.ikt[lang]}
              name="ikt"
              id="ikt"
              type="text"
              placeholder={message.ikt[lang]}
              component={this.renderField}
              // validate={required}
              accessType={accessType}
            />
          </div>

          <div className="form-row mt-3">
            <Field
              label={message.choose_faculty[lang]}
              name="faculty"
              id="faculty"
              options={faculties}
              component={this.renderSelect}
              onChange={this.handleFacultyChange}
              // validate={required}
              placeholder={message.choose_faculty[lang]}
              accessType={accessType}
            />

            <Field
              label={message.choose_speciality[lang]}
              name="speciality"
              id="speciality"
              options={specialities}
              component={this.renderSelect}
              // validate={required}
              placeholder={message.choose_speciality[lang]}
              accessType={accessType}
            />
          </div>

          <div className="form-row mt-3">
            <legend>{message.documents[lang]}</legend>
            <ul className="list-unstyled">
              <li>
                <p>
                  <a href="#">
                    {message.diploma_certificate[lang]}
                  </a>
                  <span className="">  Не отправлено</span>
                </p>
              </li>
              <li>
                <p>
                  <a href="#">
                    {message.ent_certificate[lang]}
                  </a>
                  <span className="">  Не отправлено</span>
                </p>
              </li>
            </ul>
          </div>
          <div className="form-group">
            <label htmlFor="comment">Review Comment</label>
            <textarea className="form-control" id="comment" rows="3" disabled={true}></textarea>
          </div>
          <div className="col text-right">
            <button className="btn btn-success">{message.save[lang]}</button>
            {accessType === ACCESS_TYPE_SAVE || accessType === ACCESS_TYPE_SAVE_CANCELLABLE &&
            <button
              className={"btn mt-3 " + (accessType === ACCESS_TYPE_SAVE_CANCELLABLE ? 'btn-danger' : 'btn-success')}
              type="submit" disabled={submitting}>{message.save[lang]}</button>}
            {accessType === ACCESS_TYPE_EDIT && <button className="btn mt-3 btn-warning" type="button"
                                                        onClick={this.onEditClick}>{message.edit[lang]}</button>}
            {accessType === ACCESS_TYPE_SAVE_CANCELLABLE && <button className="btn mt-3 ml-3 btn-success" type="button"
                                                                    onClick={this.onCancelClicked}>{message.cancel[lang]}</button>}
          </div>
        </form>
      </div>
    )
  }
}

const validate = (values) => {
  const errors = {}

  // if (!values.school && !values.customSchool) {
  //   errors.school = "Required"
  // }
  return errors
}

EducationInfoForm = reduxForm({
  form: 'EducationInfoForm',
  validate,
  enableReinitialize: true,
})(EducationInfoForm)

export default connect(
  state => ({
    lang: state.lang,
    areas: state.info.areas,
    cities: state.info.cities,
    schools: state.info.schools,
    faculties: state.info.faculties,
    specialities: state.info.specialities

    // cities: refactorCities(state.info.cities),
    // schools: refactorSchools(state.info.schools),
    // formValues: getFormValues('GeneralInfo')(state),
    // initialValues: refactorGeneralInfo(state.student.studentInfo)
  }),
  dispatch => ({
    fetchAreas: bindActionCreators(fetchAreas, dispatch),
    fetchCities: bindActionCreators(fetchCities, dispatch),
    fetchSchools: bindActionCreators(fetchSchools, dispatch),
    fetchFaculties: bindActionCreators(fetchFaculties, dispatch),
    fetchSpecialities: bindActionCreators(fetchSpecialities, dispatch),
    changeFieldValue: bindActionCreators(changeFieldValue, dispatch)

    // fetchSchools: bindActionCreators(fetchSchools, dispatch),
    // saveStudentPersonalInfo: bindActionCreators(saveStudentPersonalInfo, dispatch),
    // fetchStudentGeneralInfo: bindActionCreators(fetchStudentGeneralInfo, dispatch),

  })
)(EducationInfoForm)