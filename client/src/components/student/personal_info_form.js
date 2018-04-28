import React, {Component} from "react"
import "./overview_menu"
import {connect} from "react-redux"
import {bindActionCreators} from "redux"
import {Field, reduxForm, actionCreators, getFormValues, change as changeFieldValue} from "redux-form"
import MySelectComponent from "../my_select_component"
import InputMask from 'react-input-mask'
import _ from "lodash"

import {
  fetchAreas,
  fetchCities,
  fetchSchools,
  saveStudentPersonalInfo,
  fetchStudentGeneralInfo,
  saveDocument,
  savePersonalDocument,
  fetchPersonalInfo
} from "../../actions"

import {
  ACCESS_TYPE_SAVE,
  ACCESS_TYPE_EDIT,
  ACCESS_TYPE_SAVE_CANCELLABLE,

  IDENTITY_CARD_FRONT,
  IDENTITY_CARD_BACK,
  PHOTO_3x4,
} from "../../constants"

import {message} from "../../locale/message"

//validations
const required = value => (value ? undefined : 'Required')

class PersonalInfoForm extends Component {
  constructor(props) {
    super(props)

    this.state = {
      accessType: ACCESS_TYPE_SAVE,
      documentType: '',
      showDocumentErrors: '',
      isSaving: false
    }
  }

  componentDidMount() {
    const {fetchAreas, fetchPersonalInfo} = this.props
    fetchPersonalInfo(
      (data) => {
        if(data.id) {
          this.setState({accessType: ACCESS_TYPE_EDIT})
        }
      },
      () => {
        console.log('error on fetching personal info')
      }
    )
    fetchAreas()

    // fetchStudentGeneralInfo(
    //   (data) => {
    //     console.log('Data ', data)
    //     if (data.accessType === ACCESS_TYPE_SAVE) {
    //       fetchCities()
    //     } else if (data.accessType === ACCESS_TYPE_EDIT) {
    //       this.setState({accessType: ACCESS_TYPE_EDIT})
    //     }
    //   },
    //   () => {
    //     console.log('fetchStudentGeneralInfo error')
    //   }
    // )
  }

  renderField(field) {
    const {meta: {touched, error, warning}, useInputMask} = field;
    let disabled = field.accessType === ACCESS_TYPE_EDIT

    return (
      <div className="col">
        <label htmlFor={field.id}>{field.label}</label>
        {!useInputMask &&
        <input type={field.type} className="form-control" name={field.name} placeholder={field.placeholder}
               id={field.id}
               disabled={disabled} {...field.input}/>
        }
        {useInputMask &&
        <InputMask mask={field.inputMask} maskChar="_" className="form-control" name={field.name} disabled={disabled}
                   id={field.id} {...field.input}/>
        }
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
          <div className="form-check form-check-inline" key={key}>
            <input {...field.input} className="form-check-input" type="radio" name={field.name} id={options[key].name}
                   value={options[key].name} checked={field.input.value === options[key].name} disabled={disabled}/>
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

  renderDocuments = (lang, showDocumentErrors, personalInfo) => {
    console.log('personal info ', personalInfo)
    const labels = [
      {
        type: IDENTITY_CARD_FRONT,
        label: message.ud_front[lang],
        error: message.upload_file[lang],
        imageName: personalInfo.ud_front
      },
      {
        type: IDENTITY_CARD_BACK,
        label: message.ud_back[lang],
        error: message.upload_file[lang],
        imageName: personalInfo.ud_back
      },
      {
        type: PHOTO_3x4,
        label: message.photo_3x4[lang],
        error: message.upload_file[lang],
        imageName: personalInfo.photo3x4
      }
    ]

    console.log('lables ', labels)

    return labels.map(option => (
      <li>
        <p>
          <a href="#" onClick={this.exportFile} name={option.type}>
            {option.label}
          </a>
          {showDocumentErrors && !option.imageName &&
          <span className="text-danger ml-2">{option.error}</span>
          }
          {option.imageName &&
          <a className="ml-2" target="_blank" href={`http://localhost:8081/api/upload/${option.imageName}`}><i
            className="fas fa-eye"></i></a>
          }
        </p>
      </li>
    ))
  }

  onFileChange = (e) => {
    const file = e.target.files[0]
    const documentType = this.state.documentType

    const {savePersonalDocument} = this.props
    savePersonalDocument(file, documentType)
  }

  exportFile = (e) => {
    e.preventDefault()

    this.setState({documentType: e.target.name}, () => {
      //script for opening 'choose file' dialog
      const elem = document.getElementById("documentFile")
      if (elem && document.createEvent) {
        const evt = document.createEvent("MouseEvents");
        evt.initEvent("click", true, false);
        elem.dispatchEvent(evt);
      }
    })
  }

  onSubmit(values) {

    const {saveStudentPersonalInfo, personalInfo, lang} = this.props

    this.setState({showDocumentErrors: true}, () => {
      if (personalInfo.ud_front && personalInfo.ud_front && personalInfo.photo3x4) {
        this.setState({isSaving: true}, () => {
          saveStudentPersonalInfo(values,
            () => {
              this.setState({isSaving: false, accessType: ACCESS_TYPE_EDIT})
            },
            () => {
              this.setState({isSaving: false})
            }
          )
        })
      }
    })

    // saveStudentPersonalInfo(values)
    // const {saveStudentPersonalInfo, initialValues} = this.props
    // console.log('Why saving?')
    // saveStudentPersonalInfo(
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

  handleEditBtn = () => {
    this.setState({accessType: ACCESS_TYPE_SAVE})
  }


  render() {
    let {areas, cities, schools, initialValues, lang, personalInfo} = this.props
    const {showDocumentErrors, accessType} = this.state

    if (areas) {
      _.forEach(areas, (value, key) => {
        const label = lang === 'ru' ? areas[key].nameRu : lang === 'en' ? areas[key].nameEn : areas[key].nameKkk
        areas[key].value = key
        areas[key].label = label
      })
    }
    console.log('areas ', areas)

    console.log('cities ', cities)
    // const selectBoxCities = cities.m
    const {handleSubmit, submitting} = this.props


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
                  value: 'first_plus',
                  label: `1 ${message.group[lang]} +`
                },
                option2: {
                  value: 'first_minus',
                  label: `1 ${message.group[lang]} -`
                },
                option3: {
                  value: 'second_plus',
                  label: `2 ${message.group[lang]} +`
                },
                option4: {
                  value: 'second_minus',
                  label: `2 ${message.group[lang]} -`
                },
                option5: {
                  value: 'third_plus',
                  label: `3 ${message.group[lang]} +`
                },
                option6: {
                  value: 'third_minus',
                  label: `3 ${message.group[lang]} -`
                },
                option7: {
                  value: 'fourth_plus',
                  label: `4 ${message.group[lang]} +`
                },
                option8: {
                  value: 'fourth_minus',
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
              inputMask="99 99 99 99 99 99"
              useInputMask="true"
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
            <Field
              label={message.birthPlace[lang]}
              id="birthPlace"
              name="birthPlace"
              options={areas}
              lang={lang}
              component={this.renderSelect}
              accessType={accessType}
            />
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
            <Field
              label={message.tel_phone[lang]}
              name="telPhone"
              id="telPhone"
              type="text"
              component={this.renderField}
              accessType={accessType}
              useInputMask="true"
              inputMask="+7(999) 999 99 99"
            />
            <Field
              label={message.mobile_phone[lang]}
              name="mobilePhone"
              id="mobilePhone"
              type="text"
              component={this.renderField}
              accessType={accessType}
              useInputMask="true"
              inputMask="+7(999) 999 99 99"
            />
            <div className="col"/>
          </div>
          <div className="form-row mt-3">
            <legend>{message.documents[lang]}</legend>
            <ul className="list-unstyled">
              {this.renderDocuments(lang, showDocumentErrors, personalInfo)}
            </ul>
            <input style={{display: 'none'}} type="file" id="documentFile" onChange={this.onFileChange} onClick={e => {
              e.target.value = null
            }}/>
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

          <div className="col text-right">
            {accessType === ACCESS_TYPE_SAVE &&
            <button className="btn btn-success" type="submit">{message.send[lang]}</button>}
            {accessType === ACCESS_TYPE_EDIT &&
            <button className="btn btn-warning" type="button" onClick={this.handleEditBtn.bind(this)}>{message.edit[lang]}</button>}


            {/*{accessType === ACCESS_TYPE_SAVE || accessType === ACCESS_TYPE_SAVE_CANCELLABLE &&*/}
            {/*<button*/}
            {/*className={"btn mt-3 " + (accessType === ACCESS_TYPE_SAVE_CANCELLABLE ? 'btn-danger' : 'btn-success')}*/}
            {/*type="submit" disabled={submitting}>{message.save[lang]}</button>}*/}
            {/*{accessType === ACCESS_TYPE_EDIT && <button className="btn mt-3 btn-warning" type="button"*/}
            {/*onClick={this.onEditClick}>{message.edit[lang]}</button>}*/}
            {/*{accessType === ACCESS_TYPE_SAVE_CANCELLABLE && <button className="btn mt-3 ml-3 btn-success" type="button"*/}
            {/*onClick={this.onCancelClicked}>{message.cancel[lang]}</button>}*/}
          </div>
          <div className="form-group">
            <label htmlFor="comment">{message.moderator_comment[lang]}</label>
            <textarea className="form-control" id="comment" rows="3" disabled={true}></textarea>
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

function refactorGeneralInfo(personalInfo) {
  return personalInfo
}

export default connect(
  state => ({
    lang: state.lang,
    areas: state.info.areas,
    cities: refactorCities(state.info.cities),
    schools: refactorSchools(state.info.schools),
    formValues: getFormValues('PersonalInfoForm')(state),
    personalInfo: state.student.personalInfo,
    initialValues: refactorGeneralInfo(state.student.personalInfo)
  }),
  dispatch => ({
    fetchAreas: bindActionCreators(fetchAreas, dispatch),
    fetchCities: bindActionCreators(fetchCities, dispatch),
    fetchSchools: bindActionCreators(fetchSchools, dispatch),
    saveStudentPersonalInfo: bindActionCreators(saveStudentPersonalInfo, dispatch),
    fetchStudentGeneralInfo: bindActionCreators(fetchStudentGeneralInfo, dispatch),
    changeFieldValue: bindActionCreators(changeFieldValue, dispatch),
    saveDocument: bindActionCreators(saveDocument, dispatch),
    savePersonalDocument: bindActionCreators(savePersonalDocument, dispatch),
    fetchPersonalInfo: bindActionCreators(fetchPersonalInfo, dispatch)
  })
)(PersonalInfoForm)