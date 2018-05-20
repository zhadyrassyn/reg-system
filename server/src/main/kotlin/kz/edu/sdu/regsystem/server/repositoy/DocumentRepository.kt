package kz.edu.sdu.regsystem.server.repositoy

import kz.edu.sdu.regsystem.controller.model.enums.DocumentType
import kz.edu.sdu.regsystem.server.domain.Document
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository

@Repository
class DocumentRepository(val jdbcTemplate: JdbcTemplate) {

    fun save(document: Document) : Long {
        val query = "INSERT INTO DOCUMENT(ud_back, ud_front, photo3x4, school_diploma, ent_certificate, form86, form63, flurography, user_id)\n" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);"

        val keyHolder = GeneratedKeyHolder()

        jdbcTemplate.update(
            { connection ->
                var counter = 1
                val ps = connection.prepareStatement(query, arrayOf("id"))
                ps.setString(counter++, document.ud_back)
                ps.setString(counter++, document.ud_front)
                ps.setString(counter++, document.photo3x4)
                ps.setString(counter++, document.school_diploma)
                ps.setString(counter++, document.ent_certificate)
                ps.setString(counter++, document.form86)
                ps.setString(counter++, document.form63)
                ps.setString(counter++, document.flurography)
                ps.setLong(counter, document.userId)
                ps
            }, keyHolder)

        return keyHolder.key!!.toLong()
    }

    fun get(userId: Long) : Document? {
        val query = "SELECT * FROM DOCUMENT WHERE user_id=?"

        try {
            return jdbcTemplate.queryForObject(
                query, RowMapper { rs, rowNum ->
                Document(
                    id = rs.getLong("id"),
                    ud_back = rs.getString("ud_back"),
                    ud_front = rs.getString("ud_front"),
                    photo3x4 = rs.getString("photo3x4"),
                    school_diploma = rs.getString("school_diploma"),
                    ent_certificate = rs.getString("ent_certificate"),
                    form86 = rs.getString("form86"),
                    form63 = rs.getString("form63"),
                    flurography = rs.getString("flurography"),
                    userId = userId
                )
            }, userId)
        } catch (e: EmptyResultDataAccessException) {
            return null
        }
    }

    fun save(userId: Long, fileName: String, documentType: DocumentType) : Long {
        val query: String
        if(documentType == DocumentType.IDENTITY_CARD_BACK) {
            query = "INSERT INTO DOCUMENT(ud_back, ud_front, photo3x4, school_diploma, ent_certificate, form86, form63, flurography, user_id)\n" +
                "VALUES (?, null, null, null, null, null, null, null, ?)"
        } else if (documentType == DocumentType.IDENTITY_CARD_FRONT) {
            query = "INSERT INTO DOCUMENT(ud_back, ud_front, photo3x4, school_diploma, ent_certificate, form86, form63, flurography, user_id)\n" +
                "VALUES (null, ?, null, null, null, null, null, null, ?)"
        } else if (documentType == DocumentType.PHOTO_3x4) {
            query = "INSERT INTO DOCUMENT(ud_back, ud_front, photo3x4, school_diploma, ent_certificate, form86, form63, flurography, user_id)\n" +
                "VALUES (null, null, ?, null, null, null, null, null, ?)"
        } else if (documentType == DocumentType.DIPLOMA_CERTIFICATE) {
            query = "INSERT INTO DOCUMENT(ud_back, ud_front, photo3x4, school_diploma, ent_certificate, form86, form63, flurography, user_id)\n" +
                "VALUES (null, null, null, ?, null, null, null, null, ?)"
        } else if (documentType == DocumentType.UNT_CT_CERTIFICATE) {
            query = "INSERT INTO DOCUMENT(ud_back, ud_front, photo3x4, school_diploma, ent_certificate, form86, form63, flurography, user_id)\n" +
                "VALUES (null, null, null, null, ?, null, null, null, ?)"
        } else if (documentType == DocumentType.HEALTH_086) {
            query = "INSERT INTO DOCUMENT(ud_back, ud_front, photo3x4, school_diploma, ent_certificate, form86, form63, flurography, user_id)\n" +
                "VALUES (null, null, null, null, null, ?, null, null, ?)"
        } else if (documentType == DocumentType.HEALTH_063) {
            query = "INSERT INTO DOCUMENT(ud_back, ud_front, photo3x4, school_diploma, ent_certificate, form86, form63, flurography, user_id)\n" +
                "VALUES (null, null, null, null, null, null, ?, null, ?)"
        } else { //flurography
            query = "INSERT INTO DOCUMENT(ud_back, ud_front, photo3x4, school_diploma, ent_certificate, form86, form63, flurography, user_id)\n" +
                "VALUES (null, null, null, null, null, null, null, ?, ?)"
        }

        val keyHolder = GeneratedKeyHolder()

        jdbcTemplate.update(
            { connection ->
                val ps = connection.prepareStatement(query, arrayOf("id"))
                ps.setString(1, fileName)
                ps.setLong(2, userId)
                ps
            }, keyHolder)

        return keyHolder.key!!.toLong()
    }

    fun update(documentId: Long, fileName: String, documentType: DocumentType) {
        val column : String
        if (documentType == DocumentType.IDENTITY_CARD_BACK) {
            column = "ud_back"
        } else if (documentType == DocumentType.IDENTITY_CARD_FRONT) {
            column = "ud_front"
        } else if (documentType == DocumentType.PHOTO_3x4) {
            column = "photo3x4"
        } else if (documentType == DocumentType.DIPLOMA_CERTIFICATE) {
            column = "school_diploma"
        } else if (documentType == DocumentType.UNT_CT_CERTIFICATE) {
            column = "ent_certificate"
        } else if (documentType == DocumentType.HEALTH_086) {
            column = "form86"
        } else if (documentType == DocumentType.HEALTH_063) {
            column = "form63"
        } else { //flurography
            column = "flurography"
        }

        val query = "UPDATE DOCUMENT SET $column=? WHERE id=?"
        jdbcTemplate.update(query,
            {ps ->
                ps.setString(1, fileName)
                ps.setLong(2, documentId)
            })
    }
}