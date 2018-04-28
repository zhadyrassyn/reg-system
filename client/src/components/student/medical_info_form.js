import React, {Component} from "react"
import {connect} from "react-redux"
import {message} from "../../locale/message"

import {
  ACCESS_TYPE_EDIT,
  ACCESS_TYPE_SAVE_CANCELLABLE,
  ACCESS_TYPE_SAVE
} from "../../constants/index";

class MedicalInfoForm extends Component {
  render() {
    const {lang} = this.props
    const accessType = ACCESS_TYPE_SAVE
    return (
      <div className="container-fluid">
        <form>

        </form>
        <div className="form-row mt-3">
          <legend>{message.documents[lang]}</legend>
          <ul className="list-unstyled">
            <li>
              <p>
                <a href="#">
                  {message.medical_form_86[lang]}
                </a>
                <span className="">  Не отправлено</span>
              </p>
            </li>
            <li>
              <p>
                <a href="#">
                  {message.medical_form_63[lang]}
                </a>
                <span className="">  Не отправлено</span>
              </p>
            </li>
            <li>
              <p>
                <a href="#">
                  {message.flurography[lang]}
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
)(MedicalInfoForm)
