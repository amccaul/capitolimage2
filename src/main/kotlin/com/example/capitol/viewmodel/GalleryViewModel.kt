package com.example.capitol.viewmodel

import com.example.capitol.entity.CapitolImage
import java.awt.Image
import javax.swing.text.html.ImageView

class GalleryViewModel (
    var imageViewModels:Set<ImageViewModel>,
    var galleryName:String
)
{
    override fun toString(): String {
        var output : String = galleryName
        for ( item in imageViewModels )
            output += item.toString() + "\n"
        return output;
    }
}