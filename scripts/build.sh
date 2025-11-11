#!/bin/bash

# Android Exam Reference Builder
# Builds Android APK and compiles LaTeX PDF

set -e  # Exit on error

echo "======================================"
echo "Android Exam Reference Builder"
echo "======================================"
echo ""

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Get script directory
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"

echo -e "${YELLOW}Project root: $PROJECT_ROOT${NC}"
echo ""

# Step 1: Build Android App
echo "======================================"
echo "Step 1: Building Android App"
echo "======================================"

cd "$PROJECT_ROOT/HotelAppRef"

if [ ! -f "gradlew" ]; then
    echo -e "${RED}Error: gradlew not found in HotelAppRef directory${NC}"
    exit 1
fi

echo "Cleaning previous build..."
./gradlew clean

echo "Building debug APK..."
./gradlew assembleDebug

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓ Android app built successfully!${NC}"
    echo "APK location: app/build/outputs/apk/debug/app-debug.apk"
else
    echo -e "${RED}✗ Android build failed!${NC}"
    exit 1
fi

echo ""

# Step 2: Compile LaTeX PDF
echo "======================================"
echo "Step 2: Compiling LaTeX Reference PDF"
echo "======================================"

cd "$PROJECT_ROOT/reference"

if [ ! -f "android_exam_reference.tex" ]; then
    echo -e "${RED}Error: android_exam_reference.tex not found${NC}"
    exit 1
fi

# Check for LaTeX compilers
if command -v xelatex &> /dev/null; then
    LATEX_CMD="xelatex"
    LATEX_ARGS="-shell-escape -interaction=nonstopmode"
    echo "Using XeLaTeX with shell-escape..."
elif command -v pdflatex &> /dev/null; then
    LATEX_CMD="pdflatex"
    LATEX_ARGS="-interaction=nonstopmode"
    echo "Using pdflatex (minted syntax highlighting disabled)..."
else
    echo -e "${YELLOW}Warning: No LaTeX compiler found (xelatex or pdflatex)${NC}"
    echo "Skipping PDF generation. Install TeX Live to compile PDF."
    echo ""
    echo "======================================"
    echo "Build Summary"
    echo "======================================"
    echo -e "${GREEN}✓ Android APK: Built successfully${NC}"
    echo -e "${YELLOW}⚠ LaTeX PDF: Skipped (no compiler)${NC}"
    exit 0
fi

# Clean previous LaTeX build
rm -f android_exam_reference.pdf android_exam_reference.aux android_exam_reference.log \
      android_exam_reference.out android_exam_reference.toc android_exam_reference.idx \
      android_exam_reference.ind android_exam_reference.ilg android_exam_reference.glo \
      android_exam_reference.gls android_exam_reference.glg

echo "Compiling LaTeX (first pass)..."
$LATEX_CMD $LATEX_ARGS android_exam_reference.tex > /dev/null 2>&1 || true

if [ -f "android_exam_reference.idx" ]; then
    echo "Generating index..."
    makeindex android_exam_reference.idx > /dev/null 2>&1 || true
fi

if [ -f "android_exam_reference.glo" ]; then
    echo "Generating glossary..."
    makeglossaries android_exam_reference > /dev/null 2>&1 || true
fi

echo "Compiling LaTeX (second pass)..."
$LATEX_CMD $LATEX_ARGS android_exam_reference.tex > /dev/null 2>&1 || true

echo "Compiling LaTeX (final pass)..."
$LATEX_CMD $LATEX_ARGS android_exam_reference.tex > latex_build.log 2>&1

if [ -f "android_exam_reference.pdf" ]; then
    echo -e "${GREEN}✓ LaTeX PDF compiled successfully!${NC}"
    echo "PDF location: reference/android_exam_reference.pdf"
    PDF_SIZE=$(du -h android_exam_reference.pdf | cut -f1)
    echo "PDF size: $PDF_SIZE"
else
    echo -e "${RED}✗ LaTeX compilation failed!${NC}"
    echo "Check latex_build.log for errors"
    tail -30 latex_build.log
    exit 1
fi

echo ""

# Summary
echo "======================================"
echo "Build Summary"
echo "======================================"
echo -e "${GREEN}✓ Android APK: Built successfully${NC}"
echo "  Location: HotelAppRef/app/build/outputs/apk/debug/app-debug.apk"
echo ""
echo -e "${GREEN}✓ LaTeX PDF: Compiled successfully${NC}"
echo "  Location: reference/android_exam_reference.pdf"
echo "  Size: $PDF_SIZE"
echo ""
echo -e "${GREEN}All builds completed successfully!${NC}"
echo ""
echo "======================================"
echo "Next Steps"
echo "======================================"
echo "1. Test APK: Install on device/emulator"
echo "   adb install HotelAppRef/app/build/outputs/apk/debug/app-debug.apk"
echo ""
echo "2. Review PDF: Open with PDF reader"
echo "   xdg-open reference/android_exam_reference.pdf"
echo ""
