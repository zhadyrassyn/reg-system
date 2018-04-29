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
  fetchSchools
} from "../../actions";

class EducationInfoForm extends Component {

  constructor(props) {
    super(props)

    this.state = {
      accessType: ACCESS_TYPE_SAVE
    }
  }

  componentDidMount() {
    const {fetchAreas} = this.props
    fetchAreas()
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
    console.log('onChange ', city)
    
    // const {fetchSchools, initialValues} = this.props
    // fetchSchools(city.value,
    //   () => {
    //     this.props.changeFieldValue('GeneralInfo', 'school', null)
    //   },
    //   () => {
    //     console.log('error')
    //   })
  }

  onSubmit(values) {
    console.log('values ', values)
  }

  render() {
    let {lang, areas, handleSubmit, submitting} = this.props
    const {accessType} = this.state

    if (areas) {
      areas = _.map(areas).map(item => {
        const label = lang === 'ru' ? item.nameRu : lang === 'en' ? item.nameEn : item.nameKkk
        return {
          ...item,
          value: item.id,
          label
        }
      })
    }

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
            <div className="col">
              <label>{message.city[lang]}/{message.village[lang]}</label>
              <select className="form-control">
                <option>Кызылорда</option>
                <option>Арал</option>
                <option>Жанакорган</option>
              </select>
            </div>

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
            <div className="col">
              <label>{message.school[lang]}</label>
              <select className="form-control">
                <option>БИЛ</option>
                <option>НУ</option>
                <option>3</option>
              </select>
            </div>
            <div className="col">
              <label>{message.customSchool[lang]}</label>
              <input type="text" className="form-control" placeholder={message.customSchool[lang]}/>
            </div>
            <div className="col">
              <label>{message.school_finish[lang]}</label>
              <input type="date" className="form-control"/>
            </div>
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
            <div className="col">
              <label>{message.choose_faculty[lang]}</label>
              <select className="form-control">
                <option>Faculty 1</option>
                <option>Faculty 2</option>
              </select>
            </div>

            <div className="col">
              <label>{message.choose_speciality[lang]}</label>
              <select className="form-control">
                <option>Speciality 1</option>
                <option>Speciality 2</option>
              </select>
            </div>
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

    // cities: refactorCities(state.info.cities),
    // schools: refactorSchools(state.info.schools),
    // formValues: getFormValues('GeneralInfo')(state),
    // initialValues: refactorGeneralInfo(state.student.studentInfo)
  }),
  dispatch => ({
    fetchAreas: bindActionCreators(fetchAreas, dispatch),
    fetchCities: bindActionCreators(fetchCities, dispatch),
    fetchSchools: bindActionCreators(fetchSchools, dispatch),

    // fetchCities: bindActionCreators(fetchCities, dispatch),
    // fetchSchools: bindActionCreators(fetchSchools, dispatch),
    // saveStudentPersonalInfo: bindActionCreators(saveStudentPersonalInfo, dispatch),
    // fetchStudentGeneralInfo: bindActionCreators(fetchStudentGeneralInfo, dispatch),
    // changeFieldValue: bindActionCreators(changeFieldValue, dispatch)
  })
)(EducationInfoForm)