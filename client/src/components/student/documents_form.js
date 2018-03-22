import React, { Component } from "react"
import { connect } from "react-redux"
import { bindActionCreators } from "redux"

import {
  DIPLOMA_CERTIFICATE,
  UNT_CT_CERTIFICATE,
  PHOTO_3x4,
  HEALTH_086,
  HEALTH_063,
  FLUOROGRAPHY,
  IDENTITY_CARD_FRONT,
  IDENTITY_CARD_BACK
} from "../../constants"

import {
  saveDocument
} from "../../actions"
import {WAITING_FOR_RESPONSE} from "../../constants/index";

const documents = [
  {
    type: DIPLOMA_CERTIFICATE,
    text: 'Certificate of secondary education completion or the original diploma of technical and vocational (primary or secondary vocational, post-secondary) education'
  },
  {
    type: UNT_CT_CERTIFICATE,
    text: 'Certificate of UNT / CT'
  },
  {
    type: PHOTO_3x4,
    text: 'Photo 3x4',
  },
  {
    type: HEALTH_086,
    text: 'Health certificate 086-U'
  },
  {
    type: HEALTH_063,
    text: 'Health certificate 0-63'
  },
  {
    type: FLUOROGRAPHY,
    text: 'fluorography'
  },
  {
    type: IDENTITY_CARD_FRONT,
    text: 'A copy of the identity card (front side)'
  },
  {
    type: IDENTITY_CARD_BACK,
    text: 'A copy of the identity card (back side)'
  }
]

class DocumentsForm extends Component {

  constructor(props) {
    super(props)

    this.state = ({
      documentType: ''
    })
  }

  exportFile = (e) => {
    e.preventDefault()

    this.setState({documentType: e.target.name}, () => {
      //script for opening 'choose file' dialog
      const elem = document.getElementById("documentFile")
      if(elem && document.createEvent) {
        const evt = document.createEvent("MouseEvents");
        evt.initEvent("click", true, false);
        elem.dispatchEvent(evt);
      }

    })

  }

  onFileChange = (e) => {
    const file = e.target.files[0]
    const documentType = this.state.documentType

    const { saveDocument } = this.props
    saveDocument(file, documentType)
  }

  renderList = (documentsStatus) => {
    return documents.map(document => {
      let status = documentsStatus[document.type]
      let statusText = status ? `Status: ${status}` : `Status: Not send`

      const className = `ml-3 ${status === WAITING_FOR_RESPONSE ? 'text-warning' : 'text-secondary'}`
      return (
        <li key={document.type}>
          <p>
            <a href="#" onClick={this.exportFile} name={document.type}>
              {document.text}
            </a>
            <span className={className}>{statusText}</span>
          </p>
        </li>
      )
    })
  }
  render() {

    const { documentsStatus } = this.props

    return (
      <div className="container">
        <ol>
          {this.renderList(documentsStatus)}
        </ol>
        <input style={{display:'none'}} type="file" id="documentFile" onChange={this.onFileChange} onClick={e => {e.target.value = null}}/>
        <div className="form-group">
          <label htmlFor="comment">Review Comment</label>
          <textarea className="form-control" id="comment" rows="3" disabled={true}></textarea>
        </div>
      </div>
    )
  }
}

export default connect(
  state => ({
    documentsStatus: state.student.documentsStatus
  }),
  dispatch => ({
    saveDocument: bindActionCreators(saveDocument, dispatch)
  })
)(DocumentsForm)