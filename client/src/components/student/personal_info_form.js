import React, {Component} from "react"
import "./overview_menu"
import {connect} from "react-redux"
import {bindActionCreators} from "redux"
import {Field, reduxForm, actionCreators, getFormValues, change as changeFieldValue} from "redux-form"
import MySelectComponent from "../my_select_component"
import InputMask from 'react-input-mask'

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
    const {fetchCities, fetchStudentGeneralInfo} = this.props
    fetchStudentGeneralInfo(
      (data) => {
        console.log('Data ', data)
        if (data.accessType === ACCESS_TYPE_SAVE) {
          fetchCities()
        } else if (data.accessType === ACCESS_TYPE_EDIT) {
          this.setState({accessType: ACCESS_TYPE_EDIT})
        }
      },
      () => {
        console.log('fetchStudentGeneralInfo error')
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
               id={field.id}
               disabled={disabled} {...field.input}
        />
        {touched && error && <span>{error}</span>}
      </div>
    )
  }

  // renderSelect(field) {
  //   const {meta: {touched, error, warning}} = field;
  //
  //   const disabled = field.accessType === ACCESS_TYPE_EDIT
  //
  //   return (
  //     <div className="col">
  //       <label htmlFor={field.id}>{field.label}</label>
  //       <MySelectComponent options={field.options} placeholder={field.placeholder}
  //                          disabled={disabled} {...field.input}/>
  //       {touched && error && <span>{error}</span>}
  //     </div>
  //   )
  // }

  renderRadioButtons(field) {
    const {meta: {touched, error, warning}, options} = field
    let disabled = field.accessType === ACCESS_TYPE_EDIT

    return (
      <div className="col">
        <label className="d-block">{field.label}</label>
        {Object.keys(options).map((key, index) => (
          <div className="form-check form-check-inline">
            <input {...field.input} className="form-check-input" type="radio" name={field.name} id={options[key].name}
                   value={options[key].name}/>
            <label className="form-check-label" htmlFor={options[key].name}>{options[key].label}</label>
          </div>
        ))}

        {touched && error && <span className="d-block">{error}</span>}
      </div>
    )
  }

  renderSelect(field) {
    const {meta: {touched, error, warning}, options} = field
    let disabled = field.accessType === ACCESS_TYPE_EDIT

    return (
      <div className="col">
        <label className="d-block">{field.label}</label>
        <select {...field.input} className="form-control">
          <option value="">Выбрать</option>
          {Object.keys(options).map((key, index) => (
            <option value={options[key].name}>{options[key].label}</option>
          ))}
        </select>
        {touched && error && <span className="d-block">{error}</span>}
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
    console.log('values ', values)
    // const {saveStudentGeneralInfo, initialValues} = this.props
    // console.log('Why saving?')
    // saveStudentGeneralInfo(
    //   values,
    //   () => {
    //     console.log('Saved successfully')
    //     this.setState({accessType: ACCESS_TYPE_EDIT})
    //   },
    //   () => {
    //     console.log('error')
    //   }
    // )
  }

  onEditClick = () => {
    const {initialValues, fetchSchools, formValues} = this.props
    this.setState({accessType: ACCESS_TYPE_SAVE_CANCELLABLE}, () => {
      this.props.fetchCities()
      this.props.fetchSchools(formValues.city.value)
    })
  }

  onCancelClicked = () => {
    const {initialValues} = this.props
    this.props.initialize(initialValues)
    this.setState({accessType: ACCESS_TYPE_EDIT})
  }


  render() {
    let {cities, schools, initialValues, lang} = this.props

    if (cities) {
      cities.map(city => {
        return {}
      })
    }

    console.log('cities ', cities)
    // const selectBoxCities = cities.m
    const {handleSubmit, submitting} = this.props

    let accessType = this.state.accessType
    accessType = ACCESS_TYPE_SAVE

    return (
      <div className="container-fluid">
        <form onSubmit={handleSubmit(this.onSubmit.bind(this))} className="my-2">
          <div className="form-row">
            <Field
              label={message.first_name[lang]}
              name="firstName"
              id="firstName"
              type="text"
              placeholder={message.first_name[lang]}
              component={this.renderField}
              // validate={required}
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
              // validate={required}
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
              // validate={required}
              accessType={accessType}
            />

            <Field
              label={message.gender[lang]}
              name="gender"
              options={{
                option1: {
                  name: 'man',
                  label: message.gender_man[lang]
                },
                option2: {
                  name: 'woman',
                  label: message.gender_woman[lang]
                },
                option3: {
                  name: 'another',
                  label: message.gender_another[lang]
                }
              }}
              component={this.renderRadioButtons}
              accessType={accessType}
              // validate={required}
            />

            <Field
              label={message.blood_group[lang]}
              name="blood_group"
              options={{
                option1: {
                  name: 'first_plus',
                  label: `1 ${message.group[lang]} +`
                },
                option2: {
                  name: 'first_minus',
                  label: `1 ${message.group[lang]} -`
                },
                option3: {
                  name: 'second_plus',
                  label: `2 ${message.group[lang]} +`
                },
                option4: {
                  name: 'second_minus',
                  label: `2 ${message.group[lang]} -`
                },
                option5: {
                  name: 'third_plus',
                  label: `3 ${message.group[lang]} +`
                },
                option6: {
                  name: 'third_minus',
                  label: `3 ${message.group[lang]} -`
                },
                option7: {
                  name: 'fourth_plus',
                  label: `4 ${message.group[lang]} +`
                },
                option8: {
                  name: 'fourth_minus',
                  label: `4 ${message.group[lang]} -`
                }
              }}
              component={this.renderSelect}
              accessType={accessType}
              // validate={required}
            />
          </div>
          <div className="form-row mt-3">
            <Field
              label={message.iin[lang]}
              name="iin"
              id="iin"
              type="text"
              placeholder={message.iin[lang]}
              component={this.renderField}
              // validate={required}
              accessType={accessType}
            />
            <Field
              label={message.ud_number[lang]}
              name="ud_number"
              id="ud_number"
              type="text"
              placeholder={message.ud_number[lang]}
              component={this.renderField}
              // validate={required}
              accessType={accessType}
            />
            <Field
              label={message.nationality[lang]}
              name="nationality"
              id="nationality"
              type="text"
              placeholder={message.nationality[lang]}
              component={this.renderField}
              // validate={required}
              accessType={accessType}
            />
            <Field
              label={message.citizenship[lang]}
              name="citizenship"
              id="citizenship"
              type="text"
              placeholder={message.citizenship[lang]}
              component={this.renderField}
              // validate={required}
              accessType={accessType}
            />
          </div>
          {/*<div className="form-row mt-3">*/}
          {/*<Field*/}
          {/*label={message.city[lang]}*/}
          {/*name="city"*/}
          {/*id="city"*/}
          {/*options={cities}*/}
          {/*component={this.renderSelect}*/}
          {/*onChange={this.handleCityChange}*/}
          {/*validate={required}*/}
          {/*placeholder={message.city[lang]}*/}
          {/*accessType={accessType}*/}
          {/*/>*/}
          {/*</div>*/}
          <div className="form-row mt-3">
            <div className="col">
              <label>{message.birthPlace[lang]}</label>
              <select className="form-control">
                <option>Кызылординская область</option>
                <option>Кызылординская область</option>
                <option>Кызылординская область</option>
                <option>Кызылординская область</option>
              </select>
            </div>
            <Field
              label={message.birthPlaceCustom[lang]}
              id="birthPlaceCustom"
              name="birthPlaceCustom"
              type="text"
              component={this.renderField}
              placeholder={message.birthPlaceCustom[lang]}
              accessType={accessType}
            />
            <Field
              label={message.givenPlace[lang]}
              id="givenPlace"
              name="givenPlace"
              type="text"
              component={this.renderField}
              placeholder={message.givenPlace[lang]}
              accessType={accessType}
            />
            <Field
              label={message.givenDate[lang]}
              name="givenDate"
              id="givenDate"
              type="date"
              component={this.renderField}
              // validate={required}
              accessType={accessType}
            />
          </div>
          <div className="form-row mt-3">
            <legend>{message.registeredAddress[lang]}</legend>
            <Field
              label={message.street[lang]}
              name="regStreet"
              id="regStreet"
              type="text"
              component={this.renderField}
              // validate={required}
              accessType={accessType}
              placeholder={message.street[lang]}
            />
            <Field
              label={message.house[lang]}
              name="regHouse"
              id="regHouse"
              type="text"
              component={this.renderField}
              // validate={required}
              accessType={accessType}
              placeholder={message.house[lang]}
            />
            <Field
              label={message.fraction[lang]}
              name="regFraction"
              id="regFraction"
              type="text"
              component={this.renderField}
              // validate={required}
              accessType={accessType}
              placeholder={message.fraction[lang]}
            />
            <Field
              label={message.flat[lang]}
              name="regFlat"
              id="regFlat"
              type="text"
              component={this.renderField}
              // validate={required}
              accessType={accessType}
              placeholder={message.flat[lang]}
            />
          </div>
          <div className="form-row mt-3">
            <legend>{message.factAddress[lang]}</legend>
            <Field
              label={message.street[lang]}
              name="factStreet"
              id="factStreet"
              type="text"
              component={this.renderField}
              // validate={required}
              accessType={accessType}
              placeholder={message.street[lang]}
            />
            <Field
              label={message.house[lang]}
              name="factHouse"
              id="factHouse"
              type="text"
              component={this.renderField}
              // validate={required}
              accessType={accessType}
              placeholder={message.house[lang]}
            />
            <Field
              label={message.fraction[lang]}
              name="factFraction"
              id="factFraction"
              type="text"
              component={this.renderField}
              // validate={required}
              accessType={accessType}
              placeholder={message.fraction[lang]}
            />
            <Field
              label={message.flat[lang]}
              name="factFlat"
              id="factFlat"
              type="text"
              component={this.renderField}
              // validate={required}
              accessType={accessType}
              placeholder={message.flat[lang]}
            />
          </div>
          <div className="form-row mt-3">
            <legend>{message.contact_info[lang]}</legend>
            <div className="col">
              <label>{message.tel_phone[lang]}</label>
              <InputMask {...this.props} mask="+7(999) 999 99 99" maskChar="-" className="form-control"/>
            </div>
            <div className="col">
              <label>{message.mobile_phone[lang]}</label>
              <InputMask {...this.props} mask="+7(999) 999 99 99" maskChar="-" className="form-control"/>
            </div>
            <Field
              label={message.email[lang]}
              name="email"
              id="email"
              type="text"
              component={this.renderField}
              // validate={required}
              accessType={accessType}
              placeholder={message.email[lang]}
            />
          </div>
          <div className="form-row mt-3">
            <legend>{message.documents[lang]}</legend>
            <ul className="list-unstyled">
              <li>
                <p>
                  <a href="#">
                    {message.ud_front[lang]}
                  </a>
                  <span className="">  Не отправлено</span>
                </p>
              </li>
              <li>
                <p>
                  <a href="#">
                    {message.ud_back[lang]}
                  </a>
                  <span className="">  Не отправлено</span>
                </p>
              </li>
              <li>
                <a href="#">
                  {message.photo_3x4[lang]}
                </a>
                <span className="">  Не отправлено</span>
              </li>
            </ul>
          </div>
          {/*<div className="form-row mt-3">*/}
          {/*<Field*/}
          {/*label={message.school[lang]}*/}
          {/*name="school"*/}
          {/*id="school"*/}
          {/*options={schools}*/}
          {/*component={this.renderSelect}*/}
          {/*placeholder={message.school[lang]}*/}
          {/*accessType={accessType}*/}
          {/*/>*/}
          {/*<Field*/}
          {/*label={message.customSchool[lang]}*/}
          {/*id="customSchool"*/}
          {/*name="customSchool"*/}
          {/*type="text"*/}
          {/*component={this.renderField}*/}
          {/*placeholder={message.customSchool[lang]}*/}
          {/*accessType={accessType}*/}
          {/*/>*/}
          {/*</div>*/}
          <div className="form-group">
            <label htmlFor="comment">Review Comment</label>
            <textarea className="form-control" id="comment" rows="3" disabled={true}></textarea>
          </div>
          <div className="col text-right">
            <button className="btn btn-success" type="submit">Save</button>
            {/*{accessType === ACCESS_TYPE_SAVE || accessType === ACCESS_TYPE_SAVE_CANCELLABLE &&*/}
            {/*<button*/}
              {/*className={"btn mt-3 " + (accessType === ACCESS_TYPE_SAVE_CANCELLABLE ? 'btn-danger' : 'btn-success')}*/}
              {/*type="submit" disabled={submitting}>{message.save[lang]}</button>}*/}
            {/*{accessType === ACCESS_TYPE_EDIT && <button className="btn mt-3 btn-warning" type="button"*/}
                                                        {/*onClick={this.onEditClick}>{message.edit[lang]}</button>}*/}
            {/*{accessType === ACCESS_TYPE_SAVE_CANCELLABLE && <button className="btn mt-3 ml-3 btn-success" type="button"*/}
                                                                    {/*onClick={this.onCancelClicked}>{message.cancel[lang]}</button>}*/}
          </div>
        </form>
      </div>
    )
  }
}

const validate = (values) => {
  const errors = {}

  if (!values.school && !values.customSchool) {
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
  if (cities) {
    cities = cities.map(city => ({
      ...city,
      value: city.id,
      label: city.nameRu
    }))
  }

  return cities
}

function refactorSchools(schools) {
  if (schools) {
    schools = schools.map(school => ({
      ...school,
      value: school.id,
      label: school.nameRu
    }))
  }

  return schools
}

function refactorGeneralInfo(studentInfo) {
  if (studentInfo && studentInfo.city && studentInfo.school) {
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
    formValues: getFormValues('PersonalInfoForm')(state),
    // initialValues: refactorGeneralInfo(state.student.studentInfo)
  }),
  dispatch => ({
    fetchCities: bindActionCreators(fetchCities, dispatch),
    fetchSchools: bindActionCreators(fetchSchools, dispatch),
    saveStudentGeneralInfo: bindActionCreators(saveStudentGeneralInfo, dispatch),
    fetchStudentGeneralInfo: bindActionCreators(fetchStudentGeneralInfo, dispatch),
    changeFieldValue: bindActionCreators(changeFieldValue, dispatch)
  })
)(PersonalInfoForm)