import React, {Component} from "react"
import {connect} from "react-redux"

class MedicalInfoForm extends Component {
  render() {
    return (
      <div>MedicalInfoForm</div>
    )
  }
}

export default connect(
  state => ({
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
)(MedicalInfoForm)
