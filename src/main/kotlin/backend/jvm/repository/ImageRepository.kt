package backend.jvm.repository

import backend.jvm.model.Image
import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository: JpaRepository<Image, Int> {

    /**
     * Get all images of a company
     * @param imagePk_companyId
     * @return List<Image>
     */
    fun getImageByCompany_Id(company_id: Int):List<Image>
}