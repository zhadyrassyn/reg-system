import React, {Component} from "react"
import {bindActionCreators} from "redux"
import {connect} from "react-redux"
import SearchBar from "./search_bar"
import TableHeader from "./table_header"
import TableBody from "./table_body"
import Pagination from "./pagination"
import EditStudent from "./edit_student"

import Modal from "react-modal"

const customStyles = {
  content: {
    width: "100%",
    height: "100%",
    top: 0,
    left: 0,
    right: 0,
    bottom: 0
  }
}

Modal.setAppElement(".root")

import {
  fetchStudents,
  fetchStudentFullInfo,
  editGeneralInfo,
  saveDocumentsComment,
  changeDocumentStatus,
  fetchTotalAmountOfStudents,
  selectStudent,
  fetchStudentsActive,
  filter
} from "../../actions"
import {message} from "../../locale/message";

class ModeratorApp extends Component {

  constructor(props) {
    super(props)

    this.state = {
      search: "",
      currentPage: 1,
      perPage: 10,
      modalIsOpen: false
    }

    this.openModal = this.openModal.bind(this);
    this.afterOpenModal = this.afterOpenModal.bind(this);
    this.closeModal = this.closeModal.bind(this);
  }

  openModal(id) {
    this.props.selectStudent(id)
    this.setState({modalIsOpen: true})
  }

  afterOpenModal() {
    // references are now sync'd and can be accessed.
    console.log("true modal")
    document.body.style.overflow = "hidden";
  }

  closeModal() {
    this.setState({modalIsOpen: false});
    document.body.style.overflow = "auto";

    this.props.fetchStudentsActive()
  }


  componentWillMount() {
    document.body.style.backgroundColor = "#F8F6F9";
  }

  componentWillUnmount() {
    document.body.style.backgroundColor = null;
  }

  componentDidMount() {
    this.props.fetchStudentsActive()

  }

  handlePageChangeClick = (pageNum) => {
    this.setState({currentPage: pageNum})
  }

  handleSearch = (search) => {
    search = search.toLowerCase()
    const {students, lang, filter} = this.props

    let filtered = {}
    const copy = {...students}

    console.log("daniuar".indexOf(""))
    Object.keys(copy).filter(key => {
      return (copy[key].id + "").indexOf(search) >= 0 ||
        copy[key].firstName.toLowerCase().indexOf(search) >= 0 ||
        copy[key].middleName.toLowerCase().indexOf(search) >= 0 ||
        copy[key].lastName.toLowerCase().indexOf(search) >= 0 ||
        copy[key].iin.toLowerCase().indexOf(search) >= 0 ||
        message[copy[key].gender.toLowerCase()][lang].toLowerCase().indexOf(search) >= 0 ||
        message[copy[key].generalStatus.toLowerCase()][lang].toLowerCase().indexOf(search) >= 0
    }).map(id => {
      filtered[id] = students[id]
    })

    console.log('filtered ', filtered)
    filter(filtered)

    // this.props.fetchTotalAmountOfStudents(search,
    //   () => {
    //     this.props.fetchStudents(search, 1, this.state.perPage,
    //       () => {
    //         console.log('success')
    //         this.setState({currentPage: 1, search})
    //       })
    //   },
    //   () => {
    //     console.log('error on fetching students')
    //   })

  }

  saveDocumentsComment = (comment) => {
    this.props.saveDocumentsComment(this.props.selectedStudent.id, comment,
      () => {
        console.log('123')
      },
      () => {
        console.log('456')
      })
  }

  onGeneralInfoEdit = (generalInfoComment, generalInfoStatus) => {
    this.props.editGeneralInfo(this.props.selectedStudent.id, generalInfoComment, generalInfoStatus,
      () => {
        console.log('onSuccess')
      },
      () => {
        console.log('onError')
      })
  }

  changeDocumentStatus = (document, successCallback, errorCallback) => {

    this.props.changeDocumentStatus(
      this.props.selectedStudent.id,
      document,
      successCallback,
      errorCallback
    )
  }

  render() {
    const {students, selectedStudent, lang, displayData} = this.props
    const {currentPage, perPage} = this.state
    const total = Object.keys(displayData).length
    const startCounter = (currentPage - 1) * perPage

    //display 10 items
    const displayFrom = (currentPage - 1) * perPage
    const paginatedData = _.mapKeys(Object.values(displayData).slice(displayFrom, displayFrom + this.state.perPage), "id")

    return (
      <div className="wrapper">
        <SearchBar onSearch={this.handleSearch}/>
        <TableHeader lang={lang}/>
        <TableBody students={paginatedData} startCounter={startCounter} openModal={this.openModal.bind(this)} lang={lang}/>
        <Pagination currentPage={currentPage} perPage={perPage} total={total}
                    handlePageChangeClick={this.handlePageChangeClick}/>
        <Modal
          isOpen={this.state.modalIsOpen}
          onAfterOpen={this.afterOpenModal}
          onRequestClose={this.closeModal}
          style={customStyles}
          contentLabel="Example Modal">
          <EditStudent
            lang={lang}
            closeModal={this.closeModal}
            selectedStudent={selectedStudent}
            onGeneralInfoEdit={this.onGeneralInfoEdit}
            onSaveDocumentsComment={this.saveDocumentsComment}
            onDocumentStatusChange={this.changeDocumentStatus}
          />
        </Modal>
      </div>
    )
  }
}

export default connect(
  state => ({
    lang: state.lang,
    students: state.moderator.students,
    displayData: state.moderator.displayData,
    selectedStudent: state.moderator.selectedStudent,
    total: state.moderator.total,
    currentStudentId: state.moderator.currentStudentId
  }),
  dispatch => ({
    fetchStudents: bindActionCreators(fetchStudents, dispatch),
    fetchStudentsActive: bindActionCreators(fetchStudentsActive, dispatch),
    fetchStudentFullInfo: bindActionCreators(fetchStudentFullInfo, dispatch),
    editGeneralInfo: bindActionCreators(editGeneralInfo, dispatch),
    saveDocumentsComment: bindActionCreators(saveDocumentsComment, dispatch),
    changeDocumentStatus: bindActionCreators(changeDocumentStatus, dispatch),
    fetchTotalAmountOfStudents: bindActionCreators(fetchTotalAmountOfStudents, dispatch),
    selectStudent: bindActionCreators(selectStudent, dispatch),
    filter: bindActionCreators(filter, dispatch)
  })
)(ModeratorApp)