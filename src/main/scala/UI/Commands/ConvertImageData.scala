package UI.Commands

import ASCIIConvertor.{ASCIIConvertor, EmptyASCIIConvertor}
import ImageExporters.{EmptyImageExporter, ImageExporter}
import ImageFilters.{EmptyFilter, ImageFilter}
import ImageLoaders.{EmptyImageLoader, ImageLoader}

class ConvertImageData {
  var imageLoader : ImageLoader = EmptyImageLoader
  var imageExporter : ImageExporter = EmptyImageExporter
  var ASCIIConvertor : ASCIIConvertor = EmptyASCIIConvertor
  var imageFilter : ImageFilter = EmptyFilter
}
