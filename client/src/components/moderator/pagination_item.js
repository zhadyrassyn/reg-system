import React, { Component } from "react"

class PaginationItem extends Component {

  handlePageChange(pageNum) {
    this.props.pageChangeClicked(pageNum)
  }

  render() {
    const pageNum = this.props.value
    const currentPage = this.props.currentPage

    return (
      <li onClick={this.handlePageChange.bind(this, pageNum)}
          className={pageNum === currentPage ? "page-item active" : "page-ite"}><button className='page-link btn btn-link'>{pageNum}</button></li>
    )
  }

}

export default PaginationItem