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
  ACCESS_TYPE_SAVE,

  DIPLOMA_CERTIFICATE,
  UNT_CT_CERTIFICATE
} from "../../constants"

import {
  fetchAreas,
  fetchCities,
  fetchSchools,
  fetchFaculties,
  fetchSpecialities,
  saveStudentEducationInfo,
  fetchEducationInfo,
  saveEducationDocument
} from "../../actions"

//validations
const required = value => (value ? undefined : 'required')

class EducationInfoForm extends Component {

  constructor(props) {
    super(props)

    this.state = {
      accessType: ACCESS_TYPE_SAVE,
      documentType: '',
      showDocumentErrors: '',
      isSaving: false,

      isDiplomaSaving: false,
      isEntCertificateSaving: false,
    }
  }

  componentDidMount() {
    const {fetchAreas, fetchFaculties, fetchEducationInfo} = this.props
    fetchEducationInfo(
      (data) => {
        if (data.id) {
          this.setState({accessType: ACCESS_TYPE_EDIT})
        }
        fetchAreas()
        fetchFaculties(
          () => {
            console.log('success on fetching faculties')
          },
          () => {
            console.log('fail on fetching faculties')
          }
        )
      },
      () => {

      }
    )
  }

  renderField(field) {
    const {meta: {touched, error, warning}} = field;
    let disabled = field.accessType === ACCESS_TYPE_EDIT
    const lang = field.lang

    return (
      <div className="col">
        <label htmlFor={field.id}>{field.label}</label>
        <input type={field.type} className="form-control" name={field.name} placeholder={field.placeholder}
               id={field.id} disabled={disabled} {...field.input}/>

        {touched && error && <span className="text-danger">{message[error][lang]}</span>}
      </div>
    )
  }

  renderSelect(field) {
    const {meta: {touched, error, warning}} = field;
    const lang = field.lang

    const disabled = field.accessType === ACCESS_TYPE_EDIT

    return (
      <div className="col">
        <label htmlFor={field.id}>{field.label}</label>
        <MySelectComponent options={field.options} placeholder={field.placeholder}
                           disabled={disabled} {...field.input}/>
        {touched && error && <span className="text-danger">{message[error][lang]}</span>}
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
    const {saveStudentEducationInfo, educationInfoDocuments} = this.props
    console.log('values ', values)

    const saveData = {
      ...values,
      city: (values.city && values.city.id) || null,
      educationArea: (values.educationArea && values.educationArea.id) || null,
      school: (values.school && values.school.id) || null,
      faculty: (values.faculty && values.faculty.id) || null,
      speciality: (values.speciality && values.speciality.id) || null
    }

    console.log('savedData ', saveData)

    this.setState({showDocumentErrors: true}, () => {
      if (educationInfoDocuments.schoolDiploma && educationInfoDocuments.schoolDiploma !== ""
         && educationInfoDocuments.entCertificate && educationInfoDocuments.entCertificate !== "") {
        this.setState({isSaving: true}, () => {

          saveStudentEducationInfo(saveData,
            () => {
              this.setState({isSaving: false, accessType: ACCESS_TYPE_EDIT})
            },
            () => {
              this.setState({isSaving: false})
            },
            values
          )
        })
      }
    })
  }

  handleEditBtn = () => {
    this.setState({accessType: ACCESS_TYPE_SAVE})
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

  refactorDefaultOption = (item, lang) => {
    const label = lang === 'ru' ? item.nameRu : lang === 'en' ? item.nameEn : item.nameKkk
    return {
      ...item,
      value: item.id,
      label
    }
  }

  onFileChange = (e) => {
    const {saveEducationDocument} = this.props

    const file = e.target.files[0]
    const documentType = this.state.documentType

    if (documentType === DIPLOMA_CERTIFICATE) {
      this.setState({isDiplomaSaving: true}, () => {
        saveEducationDocument(file, documentType,
          () => {
            this.setState({isDiplomaSaving: false})
          },
          () => {
            this.setState({isDiplomaSaving: false})
          }
        )
      })
    } else if (documentType === UNT_CT_CERTIFICATE) {
      this.setState({isEntCertificateSaving: true}, () => {
        saveEducationDocument(file, documentType,
          () => {
            this.setState({isEntCertificateSaving: false})
          },
          () => {
            this.setState({isEntCertificateSaving: false})
          })
      })
    }
  }

  exportFile = (e) => {
    e.preventDefault()

    this.setState({documentType: e.target.name}, () => {
      //script for opening 'choose file' dialog
      const elem = document.getElementById("educationFile")
      if (elem && document.createEvent) {
        const evt = document.createEvent("MouseEvents");
        evt.initEvent("click", true, false);
        elem.dispatchEvent(evt);
      }
    })
  }

  renderDocuments = (lang, showDocumentErrors, educationInfoDocuments, isDiplomaSaving, isEntCertificateSaving) => {
    const labels = [
      {
        type: DIPLOMA_CERTIFICATE,
        label: message.diploma_certificate[lang],
        error: message.upload_file[lang],
        imageName: educationInfoDocuments.schoolDiploma,
        loading: isDiplomaSaving
      },
      {
        type: UNT_CT_CERTIFICATE,
        label: message.ent_certificate[lang],
        error: message.upload_file[lang],
        imageName: educationInfoDocuments.entCertificate,
        loading: isEntCertificateSaving
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

  render() {
    let {lang, areas, cities, schools, handleSubmit, submitting, faculties, specialities, educationInfo, educationInfoDocuments} = this.props
    const {accessType, documentType, showDocumentErrors, isSaving, isDiplomaSaving, isEntCertificateSaving} = this.state

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

    if(educationInfo.educationArea) {
      educationInfo.educationArea = this.refactorDefaultOption(educationInfo.educationArea, lang)
    }

    if(educationInfo.city) {
      educationInfo.city = this.refactorDefaultOption(educationInfo.city, lang)
    }

    if(educationInfo.school) {
      educationInfo.school = this.refactorDefaultOption(educationInfo.school, lang)
    }

    if(educationInfo.faculty) {
      educationInfo.faculty = this.refactorDefaultOption(educationInfo.faculty, lang)
    }

    if(educationInfo.speciality) {
      educationInfo.speciality = this.refactorDefaultOption(educationInfo.speciality, lang)
    }

    console.log('educationInfo ', this.props.educationInfo)
    console.log('educationInfo docs', this.props.educationInfoDocuments)

    const textAreaClassName="form-control disabled " + (educationInfo.status === "VALID" ? " btn-outline-success" : educationInfo.status === "INVALID" ?
      "btn-outline-danger" : "")
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
              validate={required}
              placeholder={message.area[lang]}
              accessType={accessType}
              lang={lang}
            />
            <Field
              label={message.city_village[lang]}
              name="city"
              id="city"
              options={cities}
              component={this.renderSelect}
              onChange={this.handleCityChange}
              placeholder={message.city_village[lang]}
              accessType={accessType}
              //validate func
              lang={lang}
            />

            <Field
              label={message.another_cityVillage[lang]}
              name="another_cityVillage"
              id="another_cityVillage"
              type="text"
              placeholder={message.another_cityVillage[lang]}
              component={this.renderField}
              // validate func
              accessType={accessType}
              lang={lang}
            />
          </div>

          <div className="form-row mt-3">
            <Field
              label={message.school[lang]}
              name="school"
              id="school"
              options={schools}
              component={this.renderSelect}
              // validate func
              placeholder={message.school[lang]}
              accessType={accessType}
              lang={lang}
            />

            <Field
              label={message.customSchool[lang]}
              name="customSchool"
              id="customSchool"
              type="text"
              placeholder={message.customSchool[lang]}
              component={this.renderField}
              // validate func
              accessType={accessType}
              lang={lang}
            />

            <Field
              label={message.school_finish[lang]}
              name="school_finish"
              id="school_finish"
              type="date"
              placeholder={message.school_finish[lang]}
              component={this.renderField}
              validate={required}
              accessType={accessType}
              lang={lang}
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
              validate={required}
              accessType={accessType}
              lang={lang}
            />
            <Field
              label={message.ent_amount[lang]}
              name="ent_amount"
              id="ent_amount"
              type="text"
              placeholder={message.ent_amount[lang]}
              component={this.renderField}
              validate={required}
              accessType={accessType}
              lang={lang}
            />
            <Field
              label={message.ikt[lang]}
              name="ikt"
              id="ikt"
              type="text"
              placeholder={message.ikt[lang]}
              component={this.renderField}
              validate={required}
              accessType={accessType}
              lang={lang}
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
              validate={required}
              placeholder={message.choose_faculty[lang]}
              accessType={accessType}
              lang={lang}
            />

            <Field
              label={message.choose_speciality[lang]}
              name="speciality"
              id="speciality"
              options={specialities}
              component={this.renderSelect}
              validate={required}
              placeholder={message.choose_speciality[lang]}
              accessType={accessType}
              lang={lang}
            />
          </div>

          <div className="form-row mt-3">
            <legend>{message.documents[lang]}</legend>
            <ul className="list-unstyled">
              {this.renderDocuments(lang, showDocumentErrors, educationInfoDocuments, isDiplomaSaving, isEntCertificateSaving)}
            </ul>
            <input style={{display: 'none'}} type="file" id="educationFile" onChange={this.onFileChange} onClick={e => {
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
            <textarea className={textAreaClassName} id="comment" rows="3" disabled={true} value={educationInfo.comment}></textarea>
          </div>
        </form>
      </div>
    )
  }
}

const validate = (values) => {
  const errors = {}

  if (!values.city && !values.another_cityVillage) {
    errors.city = "required"
  }

  if (!values.customSchool && !values.school) {
    errors.school = "required"
  }
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
    specialities: state.info.specialities,
    educationInfo: state.student.educationInfo,
    educationInfoDocuments: state.student.educationInfoDocuments,
    initialValues: state.student.educationInfo
    // cities: refactorCities(state.info.cities),
    // schools: refactorSchools(state.info.schools),
    // formValues: getFormValues('GeneralInfo')(state),

  }),
  dispatch => ({
    fetchAreas: bindActionCreators(fetchAreas, dispatch),
    fetchCities: bindActionCreators(fetchCities, dispatch),
    fetchSchools: bindActionCreators(fetchSchools, dispatch),
    fetchFaculties: bindActionCreators(fetchFaculties, dispatch),
    fetchSpecialities: bindActionCreators(fetchSpecialities, dispatch),
    saveStudentEducationInfo: bindActionCreators(saveStudentEducationInfo, dispatch),
    fetchEducationInfo: bindActionCreators(fetchEducationInfo, dispatch),
    saveEducationDocument: bindActionCreators(saveEducationDocument, dispatch),
    changeFieldValue: bindActionCreators(changeFieldValue, dispatch)

    // fetchSchools: bindActionCreators(fetchSchools, dispatch),
    // saveStudentPersonalInfo: bindActionCreators(saveStudentPersonalInfo, dispatch),
    // fetchStudentGeneralInfo: bindActionCreators(fetchStudentGeneralInfo, dispatch),

  })
)(EducationInfoForm)