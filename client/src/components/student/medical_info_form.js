import React, {Component} from "react"
import {connect} from "react-redux"
import {message} from "../../locale/message"

import {
  HEALTH_063,
  HEALTH_086,
  FLUOROGRAPHY
} from "../../constants"

import {
  saveMedicalDocument,
  fetchMedicalInfo
} from "../../actions"

import {bindActionCreators} from "redux"

class MedicalInfoForm extends Component {
  constructor(props) {
    super(props)

    this.state = {
      documentType: '',
      isForm86Saving: false,
      isForm63Saving: false,
      isFlurographySaving: false
    }
  }

  componentDidMount() {
    const {fetchMedicalInfo} = this.props
    fetchMedicalInfo(
      () => {
        console.log('success on fetching medical info')
      },
      () => {
        console.log('error on fetching medical info')
      }
    )
  }

  exportFile = (e) => {
    e.preventDefault()

    this.setState({documentType: e.target.name}, () => {
      //script for opening 'choose file' dialog
      const elem = document.getElementById("medicalFile")
      if (elem && document.createEvent) {
        const evt = document.createEvent("MouseEvents");
        evt.initEvent("click", true, false);
        elem.dispatchEvent(evt);
      }
    })
  }

  onFileChange = (e) => {
    const {saveMedicalDocument} = this.props

    const file = e.target.files[0]
    const documentType = this.state.documentType

    if (documentType === HEALTH_086) {
      this.setState({isForm86Saving: true}, () => {
        saveMedicalDocument(file, documentType,
          () => {
            this.setState({isForm86Saving: false})
          },
          () => {
            this.setState({isForm86Saving: false})
          }
        )
      })
    } else if (documentType === HEALTH_063) {
      this.setState({isForm63Saving: true}, () => {
        saveMedicalDocument(file, documentType,
          () => {
            this.setState({isForm63Saving: false})
          },
          () => {
            this.setState({isForm63Saving: false})
          })
      })
    } else if (documentType === FLUOROGRAPHY) {
      this.setState({isFlurographySaving: true}, () => {
        saveMedicalDocument(file, documentType,
          () => {
            this.setState({isFlurographySaving: false})
          },
          () => {
            this.setState({isFlurographySaving: false})
          })
      })
    }
  }

  renderDocuments = (lang, medicalInfoDocuments, isForm86Saving, isForm63Saving, isFlurographySaving) => {
    const labels = [
      {
        type: HEALTH_063,
        label: message.medical_form_63[lang],
        error: message.upload_file[lang],
        imageName: medicalInfoDocuments.form63,
        loading: isForm63Saving
      },
      {
        type: HEALTH_086,
        label: message.medical_form_86[lang],
        error: message.upload_file[lang],
        imageName: medicalInfoDocuments.form86,
        loading: isForm86Saving
      },
      {
        type: FLUOROGRAPHY,
        label: message.flurography[lang],
        error: message.upload_file[lang],
        imageName: medicalInfoDocuments.flurography,
        loading: isFlurographySaving
      }
    ]

    return labels.map(option => (
      <li key={option.type}>
        <p>
          <a href="#" onClick={this.exportFile} name={option.type}>
            {option.label}
          </a>

          {option.loading && <span className="spinner ml-3"><i className="fa fa-spinner fa-spin fa-1x"/></span>}

          {!option.imageName &&
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
    const {lang, medicalInfoDocuments} = this.props
    const {isForm86Saving, isForm63Saving, isFlurographySaving} = this.state
    console.log('medicalInfoDocuments ', medicalInfoDocuments)

    const textAreaClassName="form-control disabled " + (medicalInfoDocuments.status === "VALID" ? " btn-outline-success" : medicalInfoDocuments.status === "INVALID" ?
      "btn-outline-danger" : "")
    return (
      <div className="container-fluid">
        <div className="form-row mt-3">
          <legend>{message.documents[lang]}</legend>
          <ul className="list-unstyled">
            {this.renderDocuments(lang, medicalInfoDocuments, isForm86Saving, isForm63Saving, isFlurographySaving)}
          </ul>
          <input style={{display: 'none'}} type="file" id="medicalFile" onChange={this.onFileChange} onClick={e => {
            e.target.value = null
          }}/>
        </div>

        <div className="form-group">
          <label htmlFor="comment">{message.moderator_comment[lang]}</label>
          <textarea className={textAreaClassName} id="comment" rows="3" disabled={true} value={medicalInfoDocuments.comment}></textarea>
        </div>
      </div>
    )
  }
}

export default connect(
  state => ({
    lang: state.lang,
    medicalInfoDocuments: state.student.medicalInfoDocuments
  }),
  dispatch => ({
    saveMedicalDocument: bindActionCreators(saveMedicalDocument, dispatch),
    fetchMedicalInfo: bindActionCreators(fetchMedicalInfo, dispatch)
  })
)(MedicalInfoForm)
