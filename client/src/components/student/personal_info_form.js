import React, {Component} from "react"
import "./overview_menu"
import {connect} from "react-redux"
import {bindActionCreators} from "redux"
import {Field, reduxForm, actionCreators, getFormValues, change as changeFieldValue} from "redux-form"
import InputMask from 'react-input-mask'
import _ from "lodash"

import {
  fetchAreas,
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
const required = value => (value ? undefined : 'required')
const Length = num => value =>
  value && value.length !== num ? 'length9' : undefined
const Length9 = Length(9)

const Max = num => value =>
  value && value.length > num ? 'max3' : undefined
const Max3 = Max(3)



class PersonalInfoForm extends Component {
  constructor(props) {
    super(props)

    this.state = {
      accessType: ACCESS_TYPE_SAVE,
      documentType: '',
      showDocumentErrors: '',
      isSaving: false,

      isPhoto3x4Saving: false,
      isUdBackSaving: false,
      isUdFrontSaving: false
    }
  }

  componentDidMount() {
    const {fetchAreas, fetchPersonalInfo} = this.props
    fetchPersonalInfo(
      (data) => {
        if (data.id) {
          this.setState({accessType: ACCESS_TYPE_EDIT})
        }
      },
      () => {
        console.log('error on fetching personal info')
      }
    )
    fetchAreas()
  }

  renderField(field) {
    const {meta: {touched, error, warning}, useInputMask} = field;
    let disabled = field.accessType === ACCESS_TYPE_EDIT
    const lang = field.lang

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
        {touched && error && <span className="text-danger">{message[error][lang]}</span>}
      </div>
    )
  }

  renderRadioButtons(field) {
    const {meta: {touched, error, warning}, options} = field
    let disabled = field.accessType === ACCESS_TYPE_EDIT
    const lang = field.lang

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

        {touched && error && <span className="d-block text-danger">{message[error][lang]}</span>}
      </div>
    )
  }

  renderSelect(field) {
    const {meta: {touched, error, warning}, options} = field
    let disabled = field.accessType === ACCESS_TYPE_EDIT
    const lang = field.lang

    return (
      <div className="col">
        <label className="d-block">{field.label}</label>
        <select {...field.input} className="form-control" disabled={disabled}>
          <option value="">Выбрать</option>
          {options && Object.keys(options).map((key, index) => (
            <option value={options[key].value} key={key}>{options[key].label}</option>
          ))}
        </select>
        {touched && error && <span className="d-block text-danger">{message[error][lang]}</span>}
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

  renderDocuments = (lang, showDocumentErrors, personalInfoDocuments, isPhoto3x4Saving, isUdFrontSaving, isUdBackSaving) => {
    const labels = [
      {
        type: IDENTITY_CARD_FRONT,
        label: message.ud_front[lang],
        error: message.upload_file[lang],
        imageName: personalInfoDocuments.ud_front,
        loading: isUdFrontSaving
      },
      {
        type: IDENTITY_CARD_BACK,
        label: message.ud_back[lang],
        error: message.upload_file[lang],
        imageName: personalInfoDocuments.ud_back,
        loading: isUdBackSaving
      },
      {
        type: PHOTO_3x4,
        label: message.photo_3x4[lang],
        error: message.upload_file[lang],
        imageName: personalInfoDocuments.photo3x4,
        loading: isPhoto3x4Saving
      }
    ]

    return labels.map(option => (
      <li key={option.type}>
        <p>
          <a href="#" onClick={this.exportFile} name={option.type}>
            {option.label}
          </a>

          {option.loading && <span className="spinner ml-3"><i className="fa fa-spinner fa-spin fa-1x"/></span>}

          {showDocumentErrors && !option.imageName &&
          <span className="text-danger ml-2">{option.error}</span>
          }

          {!option.loading && option.imageName &&
          <a className="ml-2" target="_blank" href={`http://localhost:8081/api/upload/${option.imageName}`}><i
            className="fas fa-eye"></i></a>
          }
        </p>
      </li>
    ))
  }

  onFileChange = (e) => {
    const {savePersonalDocument} = this.props

    const file = e.target.files[0]
    const documentType = this.state.documentType

    if (documentType === PHOTO_3x4) {
      this.setState({isPhoto3x4Saving: true}, () => {
        savePersonalDocument(file, documentType,
          () => {
            this.setState({isPhoto3x4Saving: false})
          },
          () => {
            this.setState({isPhoto3x4Saving: false})
          }
        )
      })
    } else if (documentType === IDENTITY_CARD_BACK) {
      this.setState({isUdBackSaving: true}, () => {
        savePersonalDocument(file, documentType,
          () => {
            this.setState({isUdBackSaving: false})
          },
          () => {
            this.setState({isUdBackSaving: false})
          })
      })
    } else if (documentType === IDENTITY_CARD_FRONT) {
      this.setState({isUdFrontSaving: true}, () => {
        savePersonalDocument(file, documentType,
          () => {
            this.setState({isUdFrontSaving: false})
          },
          () => {
            this.setState({isUdFrontSaving: false})
          })
      })
    }
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
    console.log('values ', values)
    values.iin = values.iin.replace(/[()-/+_ ]/g, "")
    values.mobilePhone = values.mobilePhone.replace(/[()-/+_ ]/g, "")
    if(values.telPhone) {
      values.telPhone = values.telPhone.replace(/[()-/+_ ]/g, "")
    }

    const {saveStudentPersonalInfo, personalInfoDocuments, lang} = this.props

    this.setState({showDocumentErrors: true}, () => {
      if (personalInfoDocuments.ud_front && personalInfoDocuments.ud_front && personalInfoDocuments.photo3x4) {
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
    let {areas, cities, schools, initialValues, lang, personalInfo, personalInfoDocuments} = this.props
    const {showDocumentErrors, accessType, isSaving, isPhoto3x4Saving, isUdFrontSaving, isUdBackSaving} = this.state

    if (areas) {
      _.forEach(areas, (value, key) => {
        const label = lang === 'ru' ? areas[key].nameRu : lang === 'en' ? areas[key].nameEn : areas[key].nameKkk
        areas[key].value = key
        areas[key].label = label
      })
    }
    const textAreaClassName="form-control disabled " + (personalInfo.status === "VALID" ? " btn-outline-success" : personalInfo.status === "INVALID" ?
    "btn-outline-danger" : "")
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
              validate={required}
              accessType={accessType}
              lang={lang}
            />
            <Field
              label={message.patronymic[lang]}
              name="middleName"
              id="middleName"
              type="text"
              placeholder={message.patronymic[lang]}
              component={this.renderField}
              accessType={accessType}
              lang={lang}
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
              lang={lang}
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
              lang={lang}
            />

            <Field
              label={message.gender[lang]}
              name="gender"
              options={{
                option1: {
                  name: 'MALE',
                  label: message.gender_man[lang]
                },
                option2: {
                  name: 'FEMALE',
                  label: message.gender_woman[lang]
                },
                option3: {
                  name: 'ANOTHER',
                  label: message.gender_another[lang]
                }
              }}
              component={this.renderRadioButtons}
              accessType={accessType}
              validate={required}
              lang={lang}
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
              lang={lang}
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
              validate={required}
              accessType={accessType}
              inputMask="99 99 99 99 99 99"
              useInputMask="true"
              lang={lang}
            />
            <Field
              label={message.ud_number[lang]}
              name="ud_number"
              id="ud_number"
              type="text"
              placeholder={message.ud_number[lang]}
              component={this.renderField}
              validate={[required, Length9]}
              accessType={accessType}
              lang={lang}
            />
            <Field
              label={message.nationality[lang]}
              name="nationality"
              id="nationality"
              type="text"
              placeholder={message.nationality[lang]}
              component={this.renderField}
              validate={required}
              accessType={accessType}
              lang={lang}
            />
            <Field
              label={message.citizenship[lang]}
              name="citizenship"
              id="citizenship"
              type="text"
              placeholder={message.citizenship[lang]}
              component={this.renderField}
              validate={required}
              accessType={accessType}
              lang={lang}
            />
          </div>
          <div className="form-row mt-3">
            <Field
              label={message.birthPlace[lang]}
              id="birthPlace"
              name="birthPlace"
              options={areas}
              component={this.renderSelect}
              accessType={accessType}
              //validate func
              lang={lang}
            />
            <Field
              label={message.birthPlaceCustom[lang]}
              id="birthPlaceCustom"
              name="birthPlaceCustom"
              type="text"
              component={this.renderField}
              placeholder={message.birthPlaceCustom[lang]}
              accessType={accessType}
              //validate func
              lang={lang}
            />
            <Field
              label={message.givenPlace[lang]}
              id="givenPlace"
              name="givenPlace"
              type="text"
              component={this.renderField}
              placeholder={message.givenPlace[lang]}
              accessType={accessType}
              validate={required}
              lang={lang}
            />
            <Field
              label={message.givenDate[lang]}
              name="givenDate"
              id="givenDate"
              type="date"
              component={this.renderField}
              validate={required}
              accessType={accessType}
              lang={lang}
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
              validate={required}
              accessType={accessType}
              placeholder={message.street[lang]}
              lang={lang}
            />
            <Field
              label={message.house[lang]}
              name="regHouse"
              id="regHouse"
              type="text"
              component={this.renderField}
              validate={[required, Max3]}
              accessType={accessType}
              placeholder={message.house[lang]}
              lang={lang}
            />
            <Field
              label={message.fraction[lang]}
              name="regFraction"
              id="regFraction"
              type="text"
              component={this.renderField}
              validate={Max3}
              accessType={accessType}
              placeholder={message.fraction[lang]}
              lang={lang}
            />
            <Field
              label={message.flat[lang]}
              name="regFlat"
              id="regFlat"
              type="text"
              component={this.renderField}
              validate={Max3}
              accessType={accessType}
              placeholder={message.flat[lang]}
              lang={lang}
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
              validate={required}
              accessType={accessType}
              placeholder={message.street[lang]}
              lang={lang}
            />
            <Field
              label={message.house[lang]}
              name="factHouse"
              id="factHouse"
              type="text"
              component={this.renderField}
              validate={[required, Max3]}
              accessType={accessType}
              placeholder={message.house[lang]}
              lang={lang}
            />
            <Field
              label={message.fraction[lang]}
              name="factFraction"
              id="factFraction"
              type="text"
              component={this.renderField}
              validate={Max3}
              accessType={accessType}
              placeholder={message.fraction[lang]}
              lang={lang}
            />
            <Field
              label={message.flat[lang]}
              name="factFlat"
              id="factFlat"
              type="text"
              component={this.renderField}
              validate={Max3}
              accessType={accessType}
              placeholder={message.flat[lang]}
              lang={lang}
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
              lang={lang}
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
              lang={lang}
              validate={required}
            />
            <div className="col"/>
          </div>
          <div className="form-row mt-3">
            <legend>{message.documents[lang]}</legend>
            <ul className="list-unstyled">
              {this.renderDocuments(lang, showDocumentErrors, personalInfoDocuments, isPhoto3x4Saving, isUdFrontSaving, isUdBackSaving)}
            </ul>
            <input style={{display: 'none'}} type="file" id="documentFile" onChange={this.onFileChange} onClick={e => {
              e.target.value = null
            }}/>
          </div>

          <div className="col text-right">
            {accessType === ACCESS_TYPE_SAVE &&
            <button className="btn btn-success btn-lg" type="submit" disabled={isSaving}>
              {message.send[lang]}
              {isSaving && <span className="spinner-white ml-2"><i className="fa fa-spinner fa-spin fa-1x"/></span>}
            </button>
            }


            {accessType === ACCESS_TYPE_EDIT &&
            <button className="btn btn-warning btn-lg" type="button"
                    onClick={this.handleEditBtn.bind(this)}>{message.edit[lang]}</button>}


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
            <textarea className={textAreaClassName} id="comment" disabled={true} value={personalInfo.comment}></textarea>
          </div>
        </form>
      </div>
    )
  }
}

const validate = (values) => {
  const errors = {}

  if(values.iin && values.iin.replace(/[()-/+_ ]/g, "").length < 12) {
    errors.iin = "fill_iin"
  }

  if(values.telPhone && values.telPhone.replace(/[()-/+_ ]/g, "").length < 11) {
    errors.telPhone = "length_msisdn"
  }

  if(values.mobilePhone && values.mobilePhone.replace(/[()-/+_ ]/g, "").length < 11) {
    errors.mobilePhone = "length_msisdn"
  }

  if (!values.birthPlace && !values.birthPlaceCustom) {
    errors.birthPlace = "required"
  }
  return errors
}

PersonalInfoForm = reduxForm({
  form: 'GeneralInfo',
  validate,
  enableReinitialize: true,
})(PersonalInfoForm)

export default connect(
  state => ({
    lang: state.lang,
    areas: state.info.areas,
    formValues: getFormValues('PersonalInfoForm')(state),
    personalInfo: state.student.personalInfo,
    personalInfoDocuments: state.student.personalInfoDocuments,
    initialValues: state.student.personalInfo
  }),
  dispatch => ({
    fetchAreas: bindActionCreators(fetchAreas, dispatch),
    saveStudentPersonalInfo: bindActionCreators(saveStudentPersonalInfo, dispatch),
    fetchStudentGeneralInfo: bindActionCreators(fetchStudentGeneralInfo, dispatch),
    changeFieldValue: bindActionCreators(changeFieldValue, dispatch),
    saveDocument: bindActionCreators(saveDocument, dispatch),
    savePersonalDocument: bindActionCreators(savePersonalDocument, dispatch),
    fetchPersonalInfo: bindActionCreators(fetchPersonalInfo, dispatch)
  })
)(PersonalInfoForm)