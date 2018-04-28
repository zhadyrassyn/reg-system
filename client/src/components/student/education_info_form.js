import React, {Component} from "react"
import {connect} from "react-redux"
import {message} from "../../locale/message"

import {
  ACCESS_TYPE_EDIT,
  ACCESS_TYPE_SAVE_CANCELLABLE,
  ACCESS_TYPE_SAVE
} from "../../constants/index";

class EducationInfoForm extends Component {
  render() {
    const {lang} = this.props
    const accessType = ACCESS_TYPE_SAVE
    return (
      <div className="container-fluid">
        <form>
          <div className="form-row">
            <div className="col">
              <label>{message.area[lang]}</label>
              <select className="form-control">
                <option>Кызылордиская область</option>
                <option>Кызылордиская область</option>
                <option>Кызылордиская область</option>
              </select>
            </div>
            <div className="col">
              <label>{message.region[lang]}</label>
              <select className="form-control">
                <option>Кызылординский район</option>
                <option>Кызылординский район</option>
                <option>Кызылординский район</option>
              </select>
            </div>
            <div className="col">
              <label>{message.city[lang]}/{message.village[lang]}</label>
              <select className="form-control">
                <option>Кызылорда</option>
                <option>Арал</option>
                <option>Жанакорган</option>
              </select>
            </div>
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
            <div className="col">
              <label>{message.ent_certificate_number[lang]}</label>
              <input type="text" className="form-control" placeholder={message.ent_certificate_number[lang]}/>
            </div>
            <div className="col">
              <label>{message.ent_amount[lang]}</label>
              <input type="text" className="form-control" placeholder={message.ent_amount[lang]}/>
            </div>
            <div className="col">
              <label>{message.ikt[lang]}</label>
              <input type="text" className="form-control" placeholder={message.ikt[lang]}/>
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

export default connect(
  state => ({
    lang: state.lang
    // cities: refactorCities(state.info.cities),
    // schools: refactorSchools(state.info.schools),
    // formValues: getFormValues('GeneralInfo')(state),
    // initialValues: refactorGeneralInfo(state.student.studentInfo)
  }),
  dispatch => ({
    // fetchCities: bindActionCreators(fetchCities, dispatch),
    // fetchSchools: bindActionCreators(fetchSchools, dispatch),
    // saveStudentPersonalInfo: bindActionCreators(saveStudentPersonalInfo, dispatch),
    // fetchStudentGeneralInfo: bindActionCreators(fetchStudentGeneralInfo, dispatch),
    // changeFieldValue: bindActionCreators(changeFieldValue, dispatch)
  })
)(EducationInfoForm)