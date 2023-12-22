package UI.Commands

import ASCIIConvertor.{ASCIIConvertor, DefaultASCIIConvertor}
import ImageExporters.{EmptyImageExporter, ImageExporter}
import ImageFilters.{EmptyFilter, ImageFilter}
import ImageLoaders.{EmptyImageLoader, ImageLoader}

/**
 * What loader, exporter, convertor and filters should be used for image conversion
 */
class ConvertImageData {
  var imageLoader: ImageLoader = EmptyImageLoader
  var imageExporter: ImageExporter = EmptyImageExporter
  var ASCIIConvertor: ASCIIConvertor = DefaultASCIIConvertor
  var imageFilter: ImageFilter = EmptyFilter
}
