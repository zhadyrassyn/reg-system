import React, {Component} from "react"
import {connect} from "react-redux"
import {message} from "../../locale/message"

class CertificatesInfoForm extends Component {
  render() {
    const {lang} = this.props
    return (
      <div className="container-fluid">
        <button className="btn btn-success">{message.add_certificate[lang]}</button>
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
    // saveStudentGeneralInfo: bindActionCreators(saveStudentGeneralInfo, dispatch),
    // fetchStudentGeneralInfo: bindActionCreators(fetchStudentGeneralInfo, dispatch),
    // changeFieldValue: bindActionCreators(changeFieldValue, dispatch)
  })
)(CertificatesInfoForm)